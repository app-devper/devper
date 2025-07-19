package com.devper.app.core.domain.usecases

import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository

class LogoutUseCase(
    dispatcher: Dispatcher,
    private val loginRepo: LoginRepository,
    private val session: SessionProvider,
) : UseCase<Unit, Unit>(dispatcher.io()) {
    override suspend fun execute(param: Unit) {
        return loginRepo.logout().let {
            session.clear()
            it
        }
    }
}
