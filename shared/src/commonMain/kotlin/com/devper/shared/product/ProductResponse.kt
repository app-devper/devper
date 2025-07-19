package com.devper.shared.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("nameEn")
    val nameEn: String,
    @SerialName("description")
    val description: String,
    @SerialName("status")
    val status: String,
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("category")
    val category: String,
    @SerialName("units")
    val units: List<ProductUnitResponse>,
    @SerialName("prices")
    val prices: List<ProductPriceResponse>,
    @SerialName("stocks")
    val stocks: List<ProductStockResponse>
)

//ProductUnit(
//      id: json['id'],
//      productId: json['productId'],
//      unit: json['unit'],
//      costPrice: json['costPrice'].toDouble(),
//      size: json['size'],
//      barcode: json['barcode'],
//      volume: json['volume'].toDouble(),
//      volumeUnit: json['volumeUnit'],
//    );

// to Serializable data class
@Serializable
data class ProductUnitResponse(
    @SerialName("id")
    val id: String,
    @SerialName("productId")
    val productId: String,
    @SerialName("unit")
    val unit: String,
    @SerialName("costPrice")
    val costPrice: Double,
    @SerialName("size")
    val size: Int,
    @SerialName("barcode")
    val barcode: String,
    @SerialName("volume")
    val volume: Double,
    @SerialName("volumeUnit")
    val volumeUnit: String
)

// ProductPrice(
//      id: json['id'],
//      productId: json['productId'],
//      unitId: json['unitId'],
//      customerType: json['customerType'],
//      price: json['price'].toDouble(),
//    );

// to Serializable data class
@Serializable
data class ProductPriceResponse(
    @SerialName("id")
    val id: String,
    @SerialName("productId")
    val productId: String,
    @SerialName("unitId")
    val unitId: String,
    @SerialName("customerType")
    val customerType: String,
    @SerialName("price")
    val price: Double
)

// ProductStock(
//      id: json['id'],
//      unitId: json['unitId'],
//      productId: json['productId'],
//      receiveCode: json['receiveCode'],
//      sequence: json['sequence'],
//      lotNumber: json['lotNumber'],
//      costPrice: json['costPrice'].toDouble(),
//      price: json['price'].toDouble(),
//      import: json['import'],
//      quantity: json['quantity'],
//      expireDate: json['expireDate'],
//      importDate: json['importDate'],
//    );

// to Serializable data class
@Serializable
data class ProductStockResponse(
    @SerialName("id")
    val id: String,
    @SerialName("unitId")
    val unitId: String,
    @SerialName("productId")
    val productId: String,
    @SerialName("receiveCode")
    val receiveCode: String,
    @SerialName("sequence")
    val sequence: Int,
    @SerialName("lotNumber")
    val lotNumber: String,
    @SerialName("costPrice")
    val costPrice: Double,
    @SerialName("price")
    val price: Double,
    @SerialName("import")
    val import: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("expireDate")
    val expireDate: String,
    @SerialName("importDate")
    val importDate: String
)


