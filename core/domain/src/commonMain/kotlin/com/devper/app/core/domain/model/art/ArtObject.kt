package com.devper.app.core.domain.model.art

data class ArtObject(
    var objectNumber: String,
    var title: String,
    var description: String,
    var url: String,
    var webImage: WebImage?,
)