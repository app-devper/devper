package com.devper.server.config

data class Config(
    val secretKey: String,
    val mongoConfig: MongoConfig,
)

fun loadConfig(): Config {
    return Config(
        secretKey = System.getenv("SECRET_KEY") ?: "",
        mongoConfig = MongoConfig(
            connectionString = "mongodb+srv://common:f9mWPxwVEGGkYnca@common-syu9e.mongodb.net"
        )
    )
}