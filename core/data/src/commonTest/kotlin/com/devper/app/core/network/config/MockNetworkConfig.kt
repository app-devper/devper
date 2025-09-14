package com.devper.app.core.network.config

class MockNetworkConfig : NetworkConfig {

    override fun getHeaders(): Map<String, Any> = mapOf()

    override fun getBaseUrl(): String = "https://mock.api.com"

    override fun isDebug(): Boolean = true
}
