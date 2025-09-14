package com.devper.app.config

import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.common.thread.provideIoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppCoroutinesDispatcher : Dispatcher {
    override fun ui(): CoroutineDispatcher = Dispatchers.Main

    override fun compute(): CoroutineDispatcher = Dispatchers.Default

    override fun io(): CoroutineDispatcher = provideIoDispatcher()
}
