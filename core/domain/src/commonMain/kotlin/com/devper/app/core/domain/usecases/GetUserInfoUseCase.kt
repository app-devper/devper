package com.devper.app.core.domain.usecases

import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.user.User
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.UserRepository

class GetUserInfoUseCase(
    dispatcher: Dispatcher,
    private val userRepo: UserRepository,
    private val session: SessionProvider,
) : UseCase<Unit, User>(dispatcher.io()) {
    override suspend fun execute(param: Unit): User = userRepo.getUserInfo()
}
