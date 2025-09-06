package com.devper.app.core.data.mapper

import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.model.product.ProductPrice
import com.devper.app.core.domain.model.product.ProductStock
import com.devper.app.core.domain.model.product.ProductUnit
import com.devper.shared.product.ProductPriceResponse
import com.devper.shared.product.ProductResponse
import com.devper.shared.product.ProductStockResponse
import com.devper.shared.product.ProductUnitResponse

fun ProductResponse.toProduct(): Product =
    Product(
        id = id,
        name = name,
        nameEn = nameEn,
        description = description,
        status = status,
        createdDate = createdDate,
        category = category,
        units = units.toProductUnits(),
        prices = prices.toProductPrices(),
        stocks = stocks.toProductStocks(),
    )

fun List<ProductResponse>.toProducts(): List<Product> = map { it.toProduct() }

fun ProductPriceResponse.toProductPrice(): ProductPrice =
    ProductPrice(
        id = id,
        productId = productId,
        unitId = unitId,
        price = price,
        customerType = customerType,
    )

fun List<ProductPriceResponse>.toProductPrices(): List<ProductPrice> = map { it.toProductPrice() }

fun ProductUnitResponse.toProductUnit(): ProductUnit =
    ProductUnit(
        id = id,
        productId = productId,
        unit = unit,
        costPrice = costPrice,
        size = size,
        barcode = barcode,
        volume = volume,
        volumeUnit = volumeUnit,
    )

fun List<ProductUnitResponse>.toProductUnits(): List<ProductUnit> = map { it.toProductUnit() }

fun ProductStockResponse.toProductStock(): ProductStock =
    ProductStock(
        id = id,
        productId = productId,
        unitId = unitId,
        quantity = quantity,
        sequence = sequence,
        lotNumber = lotNumber,
        costPrice = costPrice,
        price = price,
        import = import,
        expireDate = expireDate,
        importDate = importDate,
        receiveCode = receiveCode,
    )

fun List<ProductStockResponse>.toProductStocks(): List<ProductStock> = map { it.toProductStock() }
