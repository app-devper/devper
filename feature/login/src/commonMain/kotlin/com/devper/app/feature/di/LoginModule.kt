package com.devper.app.feature.di

import com.devper.app.feature.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule =
    module {
        viewModelOf(::LoginViewModel)
    }
