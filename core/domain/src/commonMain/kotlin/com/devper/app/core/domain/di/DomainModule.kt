package com.devper.app.core.domain.di

import com.devper.app.core.domain.usecases.KeepAliveUseCase
import com.devper.app.core.domain.usecases.LoginUseCase
import com.devper.app.core.domain.usecases.LogoutUseCase
import com.devper.app.core.domain.usecases.GetUserInfoUseCase
import com.devper.app.core.domain.usecases.GetProductsUseCase
import com.devper.app.core.domain.usecases.GetProductByIdUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::KeepAliveUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::GetUserInfoUseCase)
    factoryOf(::GetProductsUseCase)
    factoryOf(::GetProductByIdUseCase)
}