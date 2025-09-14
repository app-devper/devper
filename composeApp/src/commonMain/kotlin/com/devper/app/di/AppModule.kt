package com.devper.app.di

import coil3.annotation.ExperimentalCoilApi
import coil3.network.NetworkFetcher
import coil3.network.ktor3.asNetworkClient
import com.devper.app.config.AppHost
import com.devper.app.config.AppNetworkConfig
import com.devper.app.config.AppCoroutinesDispatcher
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.data.di.dataModule
import com.devper.app.core.domain.di.domainModule
import com.devper.app.core.domain.provider.HostProvider
import com.devper.app.core.network.config.NetworkConfig
import com.devper.app.core.network.di.HttpModule
import com.devper.app.core.network.di.httpModule
import com.devper.app.feature.di.loginModule
import com.devper.app.feature.di.mainModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(ExperimentalCoilApi::class)
val appModule = module {
    includes(httpModule)

    includes(dataModule)
    includes(domainModule)

    includes(loginModule)
    includes(mainModule)

    single {
        NetworkFetcher.Factory(
            networkClient = {
                get<HttpModule>().client.asNetworkClient()
            },
        )
    }

    singleOf(::AppCoroutinesDispatcher) { bind<Dispatcher>() }

    singleOf(::AppNetworkConfig) { bind<NetworkConfig>() }

    singleOf(::AppHost) { bind<HostProvider>() }

}