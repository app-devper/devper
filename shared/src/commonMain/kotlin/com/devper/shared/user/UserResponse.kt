package com.devper.shared.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// to Serializable data class
@Serializable
data class UserResponse(
    @SerialName("id")
    val id: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("username")
    val username: String,
    @SerialName("status")
    val status: String,
    @SerialName("role")
    val role: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String,
    @SerialName("createdBy")
    val createdBy: String,
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("updatedBy")
    val updatedBy: String,
    @SerialName("updatedDate")
    val updatedDate: String
)
