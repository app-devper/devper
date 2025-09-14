package com.devper.app.config

import com.devper.app.core.data.remote.KtorPosNetwork
import com.devper.app.core.domain.provider.HostProvider

class AppHost(
    private val posNetwork: KtorPosNetwork,
) : HostProvider {
    override fun updateHost(host: String) {
        posNetwork.updateBaseUrl(host)
    }
}
