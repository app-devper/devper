package com.devper.server.service

import com.devper.server.model.User
import com.devper.server.repository.UserRepository

class UserService(userRepository: UserRepository) : ModelService<User>(userRepository)