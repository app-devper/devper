package com.devper.app.core.domain.exception

class AppException(
    val resultCode: String,
    message: String,
    private var description: String? = null
) : Exception(message) {

    fun getDesc(): String {
        return description ?: message ?: ""
    }

}