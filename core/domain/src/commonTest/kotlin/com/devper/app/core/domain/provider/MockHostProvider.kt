package com.devper.app.core.domain.provider

class MockHostProvider : HostProvider {
        var lastUpdatedHost: String? = null
        var updateHostException: Exception? = null
        var onUpdateHost: ((String) -> Unit)? = null

        override fun updateHost(host: String) {
            lastUpdatedHost = host
            onUpdateHost?.invoke(host)
            updateHostException?.let { throw it }
        }
    }