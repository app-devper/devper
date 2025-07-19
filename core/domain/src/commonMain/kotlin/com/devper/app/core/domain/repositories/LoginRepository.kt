package com.devper.app.core.domain.repositories

import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System

interface LoginRepository {
    suspend fun login(param: LoginParam): Login
    suspend fun keepAlive(): Login
    suspend fun getSystem(): System
    suspend fun logout()
    suspend fun getAccessToken(): String
    suspend fun setAccessToken(accessToken: String)
}
