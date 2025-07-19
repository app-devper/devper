package com.devper.app.core.data.mapper

import com.devper.app.core.common.exception.HttpException
import com.devper.app.core.network.exception.NetworkException

fun NetworkException.toHttpException(): HttpException {
    return HttpException(
        message = message,
        httpStatusCode = code,
        errorCode = code.toString(),
        response = response,
        throwable = this
    )
}