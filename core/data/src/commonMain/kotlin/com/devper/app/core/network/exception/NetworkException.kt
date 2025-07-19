package com.devper.app.core.network.exception

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

class NetworkException(
    override val message: String,
    override val cause: Throwable? = null,
    val code: Int,
    val response: String
) : Exception(message, cause)

suspend inline fun <reified T> HttpResponse.toBody(): T {
    return if (status.value in 200..299) {
        body()
    } else {
        throw NetworkException(
            message = status.description,
            code = status.value,
            response = bodyAsText()
        )
    }
}