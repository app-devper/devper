package com.devper.app.core.domain.usecases

import com.devper.app.core.common.Result
import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository

class LogoutUseCase(
    dispatcher: Dispatcher,
    private val loginRepo: LoginRepository,
    private val session: SessionProvider,
) : UseCase<Unit, Result<Unit>>(dispatcher.io()) {
    override suspend fun execute(param: Unit): Result<Unit>{
        return try {
            loginRepo.logout().let {
                session.clear()
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
