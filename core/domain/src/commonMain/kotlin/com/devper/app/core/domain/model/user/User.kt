package com.devper.app.core.domain.model.user

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val status: String,
    val role: String,
    val phone: String,
    val email: String,
    val createdBy: String,
    val createdDate: String,
    val updatedBy: String,
    val updatedDate: String
)
