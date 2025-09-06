package com.devper.app.core.data.mapper

import com.devper.app.core.domain.model.user.User
import com.devper.shared.user.UserResponse

fun UserResponse.toUser(): User =
    User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        status = status,
        role = role,
        phone = phone,
        email = email,
        createdBy = createdBy,
        createdDate = createdDate,
        updatedBy = updatedBy,
        updatedDate = updatedDate,
    )
