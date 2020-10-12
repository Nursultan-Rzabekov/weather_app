package com.example.test_weather_app.usecase

typealias CompletionBlock<T> = BaseCoroutinesUseCase.Request<T>.() -> Unit