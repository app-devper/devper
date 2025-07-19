package com.devper.app.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Categories

@Serializable
data class CategoriesSearch(val categoryId: Int)


