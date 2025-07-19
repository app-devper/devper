package com.devper.app.core.domain.model.art

data class WebImage(
    val guid: String,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val width: Int,
    val height: Int,
    val url: String
)