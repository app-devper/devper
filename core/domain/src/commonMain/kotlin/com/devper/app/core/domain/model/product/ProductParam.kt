package com.devper.app.core.domain.model.product

data class CreateProductParam(
    val name: String,
    val nameEn: String?,
    val description: String?,
    val price: Double,
    val costPrice: Double,
    val unit: String,
    val serialNumber: String,
    val category: String,
    val status: String
)

data class UpdateProductParam(
    val name: String,
    val nameEn: String?,
    val description: String?,
    val category: String
)

data class UpdateProductLotQuantityParam(
    val quantity: Int
)

data class ProductPriceParam(
    val productId: String,
    val unitId: String,
    val customerType: String,
    val price: Double
)

data class ProductUnitParam(
    val productId: String,
    val unit: String,
    val costPrice: Double,
    val size: Int,
    val barcode: String,
    val volume: Double,
    val volumeUnit: String
)

data class ProductStockParam(
    val productId: String,
    val unitId: String,
    val quantity: Int,
    val costPrice: Double,
    val price: Double,
    val lotNumber: String,
    val expireDate: String,
    val importDate: String
)

data class UpdateProductStockQuantityParam(
    val quantity: Int
)

data class UpdateProductStockSequenceParam(
    val productId: String,
    val stocks: List<ProductStockSequenceParam>
)

data class ProductStockSequenceParam(
    val stockId: String,
    val sequence: Int
)
