package com.devper.app.feature.di


import org.koin.dsl.module
import com.devper.app.feature.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModelOf

val loginModule = module {
    viewModelOf(::LoginViewModel)
}