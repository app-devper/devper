package com.devper.shared.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ProductRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("name")
        val name: String,
        @SerialName("nameEn")
        val nameEn: String,
        @SerialName("description")
        val description: String,
        @SerialName("price")
        val price: Double,
        @SerialName("costPrice")
        val costPrice: Double,
        @SerialName("quantity")
        val quantity: Int,
        @SerialName("unit")
        val unit: String,
        @SerialName("serialNumber")
        val serialNumber: String,
        @SerialName("lotNumber")
        val lotNumber: String,
        @SerialName("category")
        val category: String,
        @SerialName("expireDate")
        val expireDate: String,
        @SerialName("receiveId")
        val receiveId: String,
        @SerialName("status")
        val status: String,
    )
}

data class ProductPriceRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("productId")
        val productId: String,
        @SerialName("unitId")
        val unitId: String,
        @SerialName("customerType")
        val customerType: String,
        @SerialName("price")
        val price: Double,
    )
}

data class ProductUnitRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("productId")
        val productId: String,
        @SerialName("unit")
        val unit: String,
        @SerialName("costPrice")
        val costPrice: Double,
        @SerialName("size")
        val size: String,
        @SerialName("barcode")
        val barcode: String,
        @SerialName("volume")
        val volume: Double,
        @SerialName("volumeUnit")
        val volumeUnit: String,
    )
}

data class ProductStockRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("unitId")
        val unitId: String,
        @SerialName("productId")
        val productId: String,
        @SerialName("lotNumber")
        val lotNumber: String,
        @SerialName("costPrice")
        val costPrice: Double,
        @SerialName("price")
        val price: Double,
        @SerialName("quantity")
        val quantity: Int,
        @SerialName("expireDate")
        val expireDate: String,
        @SerialName("importDate")
        val importDate: String,
    )
}

data class UpdateProductStockQuantityRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("quantity")
        val quantity: Int,
    )
}

data class UpdateProductStockSequenceRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("stocks")
        val stocks: List<Stock>,
    ) {
        @Serializable
        data class Stock(
            @SerialName("stockId")
            val stockId: String,
            @SerialName("sequence")
            val sequence: String,
        )
    }
}

data class CreateProductRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("name")
        val name: String,
        @SerialName("nameEn")
        val nameEn: String,
        @SerialName("description")
        val description: String,
        @SerialName("category")
        val category: String,
        @SerialName("unit")
        val unit: String,
        @SerialName("costPrice")
        val costPrice: Double,
        @SerialName("price")
        val price: Double,
        @SerialName("serialNumber")
        val serialNumber: String,
        @SerialName("status")
        val status: String,
    )
}