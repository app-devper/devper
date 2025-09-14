package com.devper.app.core.domain.di

import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.provider.HostProvider
import com.devper.app.core.domain.provider.MockHostProvider
import com.devper.app.core.domain.provider.MockSessionProvider
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository
import com.devper.app.core.domain.repositories.MockLoginRepository
import com.devper.app.core.domain.repositories.MockUserRepository
import com.devper.app.core.domain.repositories.UserRepository
import com.devper.app.core.domain.thread.TestDispatcher
import org.koin.dsl.module

val testModule = module {
    single<Dispatcher> { TestDispatcher() }
    single<LoginRepository> { MockLoginRepository() }
    single<SessionProvider> { MockSessionProvider() }
    single<HostProvider> { MockHostProvider() }
    single<UserRepository> { MockUserRepository() }
}