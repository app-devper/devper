package com.devper.server.model

import org.bson.BsonDateTime
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class User(
    @BsonId
    @BsonProperty("_id")
    override val id: ObjectId,
    val firstName: String,
    val lastName: String,
    val username: String,
    val clientId: String,
    val password: String,
    val role: String,
    val status: String,
    val phone: String,
    val email: String,
    val createdBy: ObjectId,
    val createdDate: BsonDateTime,
    val updatedBy: ObjectId,
    val updatedDate: BsonDateTime
) : Model
