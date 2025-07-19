package com.devper.app.core.network.di

import com.devper.app.core.domain.utils.printLogI
import com.devper.app.core.network.config.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val httpModule = module {
    singleOf(::HttpModule)
}

class HttpModule(val networkConfig: NetworkConfig) {
    val client: HttpClient by lazy { createHttpClient() }
    val baseUrl: String by lazy { networkConfig.getBaseUrl() }
    val isDebug: Boolean by lazy { networkConfig.isDebug() }

    @OptIn(ExperimentalSerializationApi::class)
    private fun createHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }
            install(HttpCache)

            if (isDebug) {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            printLogI("HTTP Client", message)
                        }
                    }
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}