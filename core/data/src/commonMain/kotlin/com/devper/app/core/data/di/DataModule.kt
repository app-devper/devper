package com.devper.app.core.data.di

import com.devper.app.core.data.remote.KtorPosNetwork
import com.devper.app.core.data.remote.KtorUmNetwork
import com.devper.app.core.data.repositories.LoginRepositoryImpl
import com.devper.app.core.data.repositories.ProductRepositoryImpl
import com.devper.app.core.data.repositories.UserRepositoryImpl
import com.devper.app.core.data.storage.AppSession
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository
import com.devper.app.core.domain.repositories.ProductRepository
import com.devper.app.core.domain.repositories.UserRepository
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule =
    module {
        // Login Repository
        singleOf(::LoginRepositoryImpl) { bind<LoginRepository>() }
        // Product Repository
        singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() }
        // User Repository
        singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
        // Session Provider
        singleOf(::AppSession) { bind<SessionProvider>() }
        // Settings
        singleOf(::Settings)

        // Network
        singleOf(::KtorPosNetwork)
        singleOf(::KtorUmNetwork)
    }
