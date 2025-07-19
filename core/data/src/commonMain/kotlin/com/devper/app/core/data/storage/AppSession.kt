package com.devper.app.core.data.storage

import com.devper.app.core.domain.provider.SessionProvider

class AppSession : SessionProvider {
    private var _accessToken: String? = null
    private var _clientId: String = ""

    override fun getAccessToken(): String? {
        return _accessToken
    }

    override fun setAccessToken(accessToken: String) {
        _accessToken = accessToken
    }

    override fun setClientId(clientId: String) {
        _clientId = clientId
    }

    override fun getClientId(): String {
        return _clientId
    }

    override fun clear() {
        _accessToken = null
        _clientId = ""
    }
}