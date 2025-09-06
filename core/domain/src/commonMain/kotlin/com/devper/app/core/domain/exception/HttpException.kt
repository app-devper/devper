package com.devper.app.core.domain.exception

data class HttpException(
    override val message: String,
    val httpStatusCode: Int,
    val errorCode: String,
    val response: String,
    val throwable: Throwable,
) : Exception(message, throwable)
