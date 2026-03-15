package com.android.travelposts.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val io : CoroutineDispatcher
    val main : CoroutineDispatcher
}