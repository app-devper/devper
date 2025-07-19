package com.devper.app.core.domain.repositories

import com.devper.app.core.domain.model.user.User

interface UserRepository {
    suspend fun getUserInfo(): User
}
