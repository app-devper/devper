package com.devper.app.core.data.remote

import com.devper.app.core.network.di.HttpModule
import com.devper.app.core.network.exception.toBody
import com.devper.shared.login.LoginRequest
import com.devper.shared.login.LoginResponse
import com.devper.shared.login.SystemResponse
import com.devper.shared.user.UserResponse
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorUmNetwork(
    private val httpModule: HttpModule,
) : BaseNetwork {

    private var baseUrl: String = httpModule.baseUrl

    private val client by lazy { httpModule.client }

    private val networkConfig by lazy { httpModule.networkConfig }

    override fun updateBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return client.post("$baseUrl/api/um/v1/auth/login") {
            setBody(request.body)
            networkConfig.getHeaders().forEach { (key, value) ->
                header(key, value)
            }
        }.toBody()
    }

    suspend fun keepAlive(): LoginResponse {
        return client.get("$baseUrl/api/um/v1/auth/keep-alive") {
            networkConfig.getHeaders().forEach { (key, value) ->
                header(key, value)
            }
        }.toBody()
    }

    suspend fun getSystem(): SystemResponse {
        return client.get("$baseUrl/api/um/v1/auth/system") {
            networkConfig.getHeaders().forEach { (key, value) ->
                header(key, value)
            }
        }.toBody()
    }

    suspend fun logout() {
        return client.post("$baseUrl/api/um/v1/auth/logout") {
            networkConfig.getHeaders().forEach { (key, value) ->
                header(key, value)
            }
        }.toBody()
    }

    suspend fun getUserInfo(): UserResponse {
        return client.get("$baseUrl/api/um/v1/user/info") {
            networkConfig.getHeaders().forEach { (key, value) ->
                header(key, value)
            }
        }.toBody()
    }
}