package com.devper.server.dto

import com.devper.shared.common.CountResponse

fun Int.toResponseDto() = CountResponse(this.toLong())

fun Long.toResponseDto() = CountResponse(this)