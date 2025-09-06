package com.devper.app.core.data.mapper

import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System
import com.devper.shared.login.LoginRequest
import com.devper.shared.login.LoginResponse
import com.devper.shared.login.SystemResponse

fun LoginParam.toLoginRequest(): LoginRequest =
    LoginRequest(
        body =
            LoginRequest.Body(
                username = username,
                password = password,
                system = system,
            ),
    )

fun LoginResponse.toLogin(): Login =
    Login(
        accessToken = accessToken,
    )

fun SystemResponse.toSystem(): System =
    System(
        id = id,
        systemName = systemName,
        systemCode = systemCode,
        clientId = clientId,
        host = host,
    )
