package com.devper.app.core.data.repositories

import com.devper.app.core.data.mapper.toHttpException
import com.devper.app.core.data.mapper.toUser
import com.devper.app.core.data.remote.KtorUmNetwork
import com.devper.app.core.domain.model.user.User
import com.devper.app.core.domain.repositories.UserRepository
import com.devper.app.core.network.exception.NetworkException

class UserRepositoryImpl(
    private val umNetwork: KtorUmNetwork
) : UserRepository {

    override suspend fun getUserInfo(): User {
        try {
            umNetwork.getUserInfo().let {
                return it.toUser()
            }
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

}