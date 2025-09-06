package com.devper.app.core.domain.error

import com.devper.app.core.domain.exception.AppException
import kotlinx.serialization.json.Json

const val ERROR_CODE_NETWORK = "N-100"
const val ERROR_CODE_UNKNOWN = "A-100"
const val ERROR_CODE_ILLEGAL_ARGUMENT = "A-101"

object ErrorMapper {
    fun toAppError(throwable: Throwable?): AppException =
        when (throwable) {
            is AppException -> {
                throwable
            }

            is com.devper.app.core.domain.exception.HttpException -> {
                val res = Json.decodeFromString<Map<String, String>>(throwable.response)
                try {
                    AppException(throwable.errorCode, throwable.message, res["error"] ?: "")
                } catch (e: Exception) {
                    AppException(ERROR_CODE_NETWORK, throwable.message, throwable.message)
                }
            }

            is IllegalArgumentException -> {
                AppException(ERROR_CODE_ILLEGAL_ARGUMENT, throwable.message ?: "", throwable.message)
            }

            else -> {
                throwable?.printStackTrace()
                AppException(ERROR_CODE_UNKNOWN, throwable?.message ?: "", throwable?.message)
            }
        }
}
