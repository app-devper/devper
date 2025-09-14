package com.devper.app.core.domain.provider

class MockSessionProvider : SessionProvider {
        var lastSetAccessToken: String? = null
        var setAccessTokenException: Exception? = null
        var onSetAccessToken: ((String) -> Unit)? = null

        var lastSetClientId: String? = null
        var setClientIdException: Exception? = null
        var onSetClientId: ((String) -> Unit)? = null

        var accessTokenResponse: String? = null
        var getAccessTokenException: Exception? = null

        var clientIdResponse: String = ""
        var getClientIdException: Exception? = null

        var clearException: Exception? = null
        var clearCalled = false
        var onClear: (() -> Unit)? = null

        override fun setAccessToken(accessToken: String) {
            lastSetAccessToken = accessToken
            onSetAccessToken?.invoke(accessToken)
            setAccessTokenException?.let { throw it }
        }

        override fun getAccessToken(): String? {
            getAccessTokenException?.let { throw it }
            return accessTokenResponse
        }

        override fun setClientId(clientId: String) {
            lastSetClientId = clientId
            onSetClientId?.invoke(clientId)
            setClientIdException?.let { throw it }
        }

        override fun getClientId(): String {
            getClientIdException?.let { throw it }
            return clientIdResponse
        }

        override fun clear() {
            clearCalled = true
            onClear?.invoke()
            clearException?.let { throw it }
        }
    }