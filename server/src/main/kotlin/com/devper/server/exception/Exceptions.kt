package com.devper.server.exception

class ValidationException(message: String) : Exception(message)

class NotFoundException(message: String? = null) : Exception(message)
