package com.devper.app.core.network.config

interface NetworkConfig {
    fun getHeaders(): Map<String, Any>
    fun getBaseUrl(): String
    fun isDebug(): Boolean
}