package com.devper.app.core.domain.provider

import com.devper.app.core.domain.model.login.System

interface SessionProvider {
    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String)
    fun setClientId(clientId: String)
    fun getClientId(): String
    fun clear()
}

fun SessionProvider.setSystem(system: System) {
    setClientId(system.clientId)
}