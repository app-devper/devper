package com.devper.app.core.domain.repositories

import com.devper.app.core.domain.model.user.User

class MockUserRepository : UserRepository {
    var userInfoResponse: User? = null
    var getUserInfoException: Exception? = null
    var getUserInfoCalled = false
    var onGetUserInfo: (() -> Unit)? = null

    override suspend fun getUserInfo(): User {
        getUserInfoCalled = true
        onGetUserInfo?.invoke()
        getUserInfoException?.let { throw it }
        return userInfoResponse ?: throw RuntimeException("No user info response configured")
    }
}
