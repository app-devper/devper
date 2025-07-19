package com.devper.server.dto

import com.devper.shared.common.IdResponse
import org.bson.BsonObjectId

fun BsonObjectId.toResponseDto() = IdResponse(
    id = value.toHexString(),
)