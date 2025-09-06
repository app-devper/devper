package com.devper.app.core.data.remote

import com.devper.app.core.network.di.HttpModule
import com.devper.app.core.network.exception.toBody
import com.devper.shared.product.ProductResponse
import io.ktor.client.request.get
import io.ktor.client.request.header

class KtorPosNetwork(
    private val httpModule: HttpModule,
) : BaseNetwork {
    private var baseUrl: String = httpModule.baseUrl

    private val client by lazy { httpModule.client }

    private val networkConfig by lazy { httpModule.networkConfig }

    override fun updateBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    suspend fun getProducts(): List<ProductResponse> =
        client
            .get("$baseUrl/api/pos/v1/products") {
                networkConfig.getHeaders().forEach { (key, value) ->
                    header(key, value)
                }
            }.toBody()
}
