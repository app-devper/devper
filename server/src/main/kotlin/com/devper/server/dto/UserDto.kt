package com.devper.server.dto

import com.devper.server.model.User
import com.devper.shared.user.UserResponse

import org.bson.BsonDateTime
import org.bson.types.ObjectId

fun User.toDto() = UserResponse(
    id = id.toHexString(),
    firstName = firstName,
    lastName = lastName,
    username = username,
    role = role,
    status = status,
    phone = phone,
    email = email,
    createdBy = createdBy.toHexString(),
    createdDate = createdDate.toString(),
    updatedDate = updatedDate.toString(),
    updatedBy = updatedBy.toHexString(),
)

fun UserResponse.toModel() = User(
    id = ObjectId(id),
    firstName = firstName,
    lastName = lastName,
    username = username,
    role = role,
    status = status,
    phone = phone,
    email = email,
    createdBy = ObjectId(createdBy),
    updatedBy = ObjectId(updatedBy),
    password = "",
    createdDate = BsonDateTime(0),
    updatedDate = BsonDateTime(0),
    clientId = "",
)
