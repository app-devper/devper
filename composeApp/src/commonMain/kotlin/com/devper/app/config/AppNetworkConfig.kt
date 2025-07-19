package com.devper.app.config
import isDebug
import com.devper.app.core.network.config.NetworkConfig
import com.devper.app.core.domain.provider.SessionProvider

class AppNetworkConfig(
    private val session: SessionProvider
) : NetworkConfig {

    override fun getHeaders(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            if (session.getAccessToken() != null) {
                put("Authorization", "Bearer ${session.getAccessToken()}")
            }
        }
    }

    override fun getBaseUrl(): String {
        return "https://um-api-th3taayg7q-as.a.run.app"
    }

    override fun isDebug(): Boolean {
        return isDebug
    }
}