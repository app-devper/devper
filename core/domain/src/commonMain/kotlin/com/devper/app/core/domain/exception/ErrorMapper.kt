package com.devper.app.core.domain.exception

import com.devper.app.core.common.exception.HttpException
import kotlinx.serialization.json.Json

object ErrorMapper {
    fun toAppError(throwable: Throwable?): AppException {
        return when (throwable) {
            is AppException -> {
                throwable
            }

            is HttpException -> {
                val res = Json.decodeFromString<Map<String, String>>(throwable.response)
                try {
                    AppException(throwable.errorCode, throwable.message, res["error"] ?: "")
                } catch (e: Exception) {
                    AppException("N-100", throwable.message, throwable.message)
                }
            }

            is IllegalArgumentException -> {
                AppException("A-101", throwable.message ?: "", throwable.message)
            }

            else -> {
                throwable?.printStackTrace()
                AppException("A-100", throwable?.message ?: "", throwable?.message)
            }
        }
    }
}