package com.example.test_weather_app.usecase

import com.example.test_weather_app.utils.NetworkErrorUiModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext


abstract class  BaseCoroutinesUseCase<T> {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(): T

    fun execute(block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
                }
                response(result)
            } catch (ex: CancellationException) {
                Timber.d(ex)
                response(ex)
            } catch (ex: ConnectException) {
                Timber.e(ex)
                response(NetworkErrorUiModel(0, ex.message))
            } catch (ex: HttpException) {
                val responseBody = ex.response()?.errorBody()
                val error = if (responseBody?.contentType()?.subtype() == "json") {
                    val errorResponse = Gson().fromJson(responseBody.string(), ErrorResponse::class.java)
                    NetworkErrorUiModel(ex.code(), errorResponse.message)
                } else {
                    NetworkErrorUiModel(ex.code(), ex.message())
                }
                Timber.e(error.toString())
                response(error)
            } catch (ex: Exception) {
                Timber.e(ex)
                response(ex)
            }
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onNetworkError: ((NetworkErrorUiModel) -> Unit)? = null
        private var onError: ((Exception) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onNetworkError(block: (NetworkErrorUiModel) -> Unit) {
            onNetworkError = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: NetworkErrorUiModel) {
            onNetworkError?.invoke(error)
        }

        operator fun invoke(error: Exception) {
            onError?.invoke(error)
        }

    }
}

data class ErrorResponse(
    @SerializedName("message") val message: String?
)