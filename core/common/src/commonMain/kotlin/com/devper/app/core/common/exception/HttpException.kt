package com.devper.app.core.common.exception

data class HttpException(
    override val message: String,
    val httpStatusCode: Int,
    val errorCode: String,
    val response: String,
    val throwable: Throwable
) : Exception(message, throwable)