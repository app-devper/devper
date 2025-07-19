package com.devper.server.repository

import com.devper.server.config.Mongo
import com.devper.server.model.User
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase

class UserRepository(mongo: Mongo) : MongoCrudRepository<User>(mongo, "um") {
    override fun MongoDatabase.selectRepositoryCollection(): MongoCollection<User> {
        return getCollection<User>("users")
    }
}