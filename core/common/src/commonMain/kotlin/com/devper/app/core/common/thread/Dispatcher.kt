package com.devper.app.core.common.thread

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun ui(): CoroutineDispatcher
    fun compute(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}

expect fun provideIoDispatcher(): CoroutineDispatcher