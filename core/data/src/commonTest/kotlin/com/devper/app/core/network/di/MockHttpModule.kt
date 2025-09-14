package com.devper.app.core.network.di

import com.devper.app.core.domain.utils.printLogI
import com.devper.app.core.network.config.MockNetworkConfig
import com.devper.app.core.network.config.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class MockHttpModule(
    networkConfig: MockNetworkConfig,
) : HttpModule(networkConfig) {

    fun clearMockResponses() {
        mockResponses.clear()
    }

    fun addMockResponse(url: String, content: String, status: HttpStatusCode) {
        mockResponses.add(
            MockResponse(
                urlEncodedPath = url,
                responseContent = content,
                responseStatus = status
            )
        )
    }

    val mockResponses = mutableListOf<MockResponse>()

    init {
        client = createMockHttpClient(mockResponses)
    }
}

data class MockResponse(
    val urlEncodedPath: String,
    val responseContent: String,
    val responseStatus: HttpStatusCode = HttpStatusCode.OK,
    val responseHeaders: Map<String, String> = mapOf(HttpHeaders.ContentType to ContentType.Application.Json.toString())
)

@OptIn(ExperimentalSerializationApi::class)
fun createMockHttpClient(
    mockResponses: List<MockResponse> = emptyList()
): HttpClient {
    return HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                },
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    printLogI("Mock HTTP Client", message)
                }
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    in mockResponses.map { it.urlEncodedPath } -> {
                        val mockResponse = mockResponses.first { it.urlEncodedPath == request.url.encodedPath }
                        respond(
                            content = mockResponse.responseContent,
                            status = mockResponse.responseStatus,
                            headers = headersOf(*mockResponse.responseHeaders.map { it.key to listOf(it.value) }.toTypedArray())
                        )
                    }

                    else -> {
                        respond(
                            content = """{"error": "Not Found"}""",
                            status = HttpStatusCode.NotFound,
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        )
                    }
                }
            }
        }
    }
}


