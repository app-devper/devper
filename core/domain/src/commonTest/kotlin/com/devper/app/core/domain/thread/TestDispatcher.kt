package com.devper.app.core.domain.thread

import com.devper.app.core.common.thread.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcher() : Dispatcher {
    override fun ui(): CoroutineDispatcher = Dispatchers.Default
    override fun compute(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.Default
}