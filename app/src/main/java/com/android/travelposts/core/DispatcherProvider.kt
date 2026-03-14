package com.android.travelposts.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
}