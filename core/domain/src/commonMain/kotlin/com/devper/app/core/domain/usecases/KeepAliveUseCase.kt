package com.devper.app.core.domain.usecases

import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.provider.HostProvider
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.provider.setSystem
import com.devper.app.core.domain.repositories.LoginRepository

class KeepAliveUseCase(
    dispatcher: Dispatcher,
    private val loginRepo: LoginRepository,
    private val session: SessionProvider,
    private val host: HostProvider,
) : UseCase<Unit, Login>(dispatcher.io()) {
    override suspend fun execute(param: Unit): Login {
        val accessToken = loginRepo.getAccessToken()
        require(accessToken.isNotEmpty()) { "Access token is empty" }
        session.setAccessToken(accessToken)
        return loginRepo.keepAlive().let {
            session.setAccessToken(it.accessToken)
            loginRepo.setAccessToken(it.accessToken)
            val system = loginRepo.getSystem()
            session.setSystem(system)
            host.setSystem(system)
            it
        }
    }
}
