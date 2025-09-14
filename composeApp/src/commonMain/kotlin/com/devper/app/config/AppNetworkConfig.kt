package com.devper.app.config
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.network.config.NetworkConfig
import isDebug

class AppNetworkConfig(
    private val session: SessionProvider,
) : NetworkConfig {
    override fun getHeaders(): Map<String, Any> =
        HashMap<String, Any>().apply {
            if (session.getAccessToken() != null) {
                put("Authorization", "Bearer ${session.getAccessToken()}")
            }
        }

    override fun getBaseUrl(): String = "https://um-api-th3taayg7q-as.a.run.app"

    override fun isDebug(): Boolean = isDebug
}
