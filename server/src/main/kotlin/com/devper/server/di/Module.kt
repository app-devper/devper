package com.devper.server.di

import org.koin.core.module.dsl.singleOf
import com.devper.server.config.AuthConfig
import com.devper.server.config.Mongo
import org.koin.dsl.module
import java.security.MessageDigest
import com.devper.server.service.UserService
import com.devper.server.repository.UserRepository
import com.devper.server.config.loadConfig
import com.devper.server.util.Md5Sum

val appModule = module {
    factory { Md5Sum(MessageDigest.getInstance("MD5")) }
    single { loadConfig() }
    singleOf(::Mongo)
    singleOf(::UserService)
    singleOf(::UserRepository)
}

val authModule = module {
    singleOf(::AuthConfig)
}

