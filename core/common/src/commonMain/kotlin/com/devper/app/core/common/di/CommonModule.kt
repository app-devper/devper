package com.devper.app.core.common.di

import com.devper.app.core.common.thread.CoroutinesDispatcher
import com.devper.app.core.common.thread.Dispatcher
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::CoroutinesDispatcher) { bind<Dispatcher>() }
}



