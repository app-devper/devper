package com.devper.server.config

import com.mongodb.kotlin.client.coroutine.MongoClient

class Mongo(
   config: Config
) {
    companion object {
        const val MONGO_ID_FIELD = "_id"
    }

    val client = MongoClient.create(config.mongoConfig.connectionString)
}