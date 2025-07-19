package com.devper.server.model

import org.bson.types.ObjectId

interface Model {
    val id: ObjectId
}