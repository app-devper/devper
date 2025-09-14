package com.devper.app.core.domain.repositories

import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System

class MockLoginRepository : LoginRepository {
        var loginResponse: Login? = null
        var loginException: Exception? = null
        var lastLoginParam: LoginParam? = null

        var systemResponse: System? = null
        var getSystemException: Exception? = null

        var lastSetAccessToken: String? = null
        var setAccessTokenException: Exception? = null
        var onSetAccessToken: ((String) -> Unit)? = null

        var accessTokenResponse: String = ""
        var getAccessTokenException: Exception? = null

        var keepAliveResponse: Login? = null
        var keepAliveException: Exception? = null

        var logoutException: Exception? = null
        var logoutCalled = false
        var onLogout: (() -> Unit)? = null
        var onGetSystem: (() -> Unit)? = null

        override suspend fun login(param: LoginParam): Login {
            lastLoginParam = param
            loginException?.let { throw it }
            return loginResponse ?: throw RuntimeException("No login response configured")
        }

        override suspend fun keepAlive(): Login {
            keepAliveException?.let { throw it }
            return keepAliveResponse ?: throw RuntimeException("No keep alive response configured")
        }

        override suspend fun getSystem(): System {
            onGetSystem?.invoke()
            getSystemException?.let { throw it }
            return systemResponse ?: throw RuntimeException("No system response configured")
        }

        override suspend fun logout() {
            logoutCalled = true
            onLogout?.invoke()
            logoutException?.let { throw it }
        }

        override suspend fun getAccessToken(): String {
            getAccessTokenException?.let { throw it }
            return accessTokenResponse
        }

        override suspend fun setAccessToken(accessToken: String) {
            lastSetAccessToken = accessToken
            onSetAccessToken?.invoke(accessToken)
            setAccessTokenException?.let { throw it }
        }
    }