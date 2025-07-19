package com.devper.app.core.data.repositories

import com.devper.app.core.data.mapper.toHttpException
import com.devper.app.core.data.mapper.toLogin
import com.devper.app.core.data.mapper.toLoginRequest
import com.devper.app.core.data.mapper.toSystem
import com.devper.app.core.data.remote.KtorUmNetwork
import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System
import com.devper.app.core.domain.repositories.LoginRepository
import com.devper.app.core.network.exception.NetworkException
import com.russhwolf.settings.Settings

class LoginRepositoryImpl(
    private val umNetwork: KtorUmNetwork,
    private val settings: Settings
) : LoginRepository {

    override suspend fun login(param: LoginParam): Login {
        try {
            umNetwork.login(param.toLoginRequest()).let {
                return it.toLogin()
            }
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

    override suspend fun keepAlive(): Login {
        try {
            umNetwork.keepAlive().let {
                return it.toLogin()
            }
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

    override suspend fun getSystem(): System {
        try {
            umNetwork.getSystem().let {
                return it.toSystem()
            }
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

    override suspend fun logout() {
        try {
            umNetwork.logout()
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

    override suspend fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    override suspend fun setAccessToken(accessToken: String) {
        settings.putString(ACCESS_TOKEN, accessToken)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }

}