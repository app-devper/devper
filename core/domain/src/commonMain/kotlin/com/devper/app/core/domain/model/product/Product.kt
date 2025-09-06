package com.devper.app.core.domain.model.product

data class Product(
    val id: String = "",
    var name: String = "",
    var nameEn: String? = null,
    var description: String? = null,
    var status: String = "",
    var category: String = "",
    var createdDate: String = "",
    var units: List<ProductUnit> = emptyList(),
    var prices: List<ProductPrice> = emptyList(),
    var stocks: List<ProductStock> = emptyList(),
) {
    fun toProductItems(): List<ProductUnitItem> {
        val items = mutableListOf<ProductUnitItem>()
        for (unit in units) {
            val prices = getProductPricesByUnitId(unit.id)
            val stocks = getProductStocksByUnitId(unit.id)
            items.add(
                ProductUnitItem(
                    id = id,
                    name = name,
                    nameEn = nameEn,
                    description = description,
                    category = category,
                    status = status,
                    createdDate = createdDate,
                    unit = unit,
                    prices = prices,
                    stocks = stocks,
                ),
            )
        }
        return items
    }

    fun getQuantity(): Int = stocks.fold(0) { previousValue, stock -> previousValue + stock.quantity }

    fun getQuantityByUnitId(unitId: String): Int =
        stocks
            .filter { it.unitId == unitId }
            .fold(0) { previousValue, stock -> previousValue + stock.quantity }

    fun getProductPricesByUnitId(unitId: String): List<ProductPrice> = prices.filter { it.unitId == unitId }

    fun defaultProductPriceByUnitId(unitId: String): ProductPrice = prices.first { it.unitId == unitId && it.customerType == "General" }

    fun getProductStocksByUnitId(unitId: String): List<ProductStock> {
        val items = stocks.filter { it.unitId == unitId }
        return items.sortedBy { it.sequence }
    }
}

data class ProductUnitItem(
    val id: String,
    val name: String,
    val nameEn: String?,
    val description: String?,
    val category: String,
    val status: String,
    val createdDate: String,
    val unit: ProductUnit,
    val prices: List<ProductPrice>,
    var stocks: List<ProductStock>,
) {
    fun getPrice(customerType: String): ProductPriceType {
        val stock = firstSequenceStock()
        if (customerType == PRICE_TYPE_STOCK) {
            if (stock != null && stock.price > 0) {
                return ProductPriceType(
                    stock = stock,
                    type = PRICE_TYPE_STOCK,
                    price = stock.price,
                    costPrice = if (stock.costPrice > 0) stock.costPrice else unit.costPrice,
                )
            }
        }
        if (prices.isNotEmpty()) {
            val price = prices.firstOrNull { it.customerType == customerType }
            return if (price != null) {
                ProductPriceType(
                    stock = stock,
                    type = price.customerType,
                    price = price.price,
                    costPrice = if (stock != null && stock.costPrice > 0) stock.costPrice else unit.costPrice,
                )
            } else {
                ProductPriceType(
                    stock = stock,
                    type = prices.first().customerType,
                    price = prices.first().price,
                    costPrice = if (stock != null && stock.costPrice > 0) stock.costPrice else unit.costPrice,
                )
            }
        } else {
            return ProductPriceType(
                stock = stock,
                type = "",
                price = 0.0,
                costPrice = if (stock != null && stock.costPrice > 0) stock.costPrice else unit.costPrice,
            )
        }
    }

    fun firstSequenceStock(): ProductStock? {
        stocks = stocks.sortedBy { it.sequence }
        for (element in stocks) {
            if (element.quantity > 0) {
                return element
            }
        }
        return null
    }

    fun getQuantity(): Int = stocks.fold(0) { previousValue, stock -> previousValue + stock.quantity }

    fun updateProductStockSequence(items: List<ProductStock>) {
        stocks = items
    }

    fun getProductStockById(stockId: String?): ProductStock? = stocks.firstOrNull { it.id == stockId }
}

data class ProductPriceType(
    val stock: ProductStock?,
    val type: String,
    val price: Double,
    val costPrice: Double,
)

data class ProductStock(
    val id: String,
    val unitId: String,
    val productId: String,
    var receiveCode: String,
    var sequence: Int,
    var lotNumber: String,
    var costPrice: Double,
    var price: Double,
    var import: Int,
    var quantity: Int,
    var expireDate: String,
    var importDate: String,
)

data class ProductPrice(
    val id: String,
    val productId: String,
    val unitId: String,
    var customerType: String,
    var price: Double,
) {
    fun getCustomerTypePrice(): String =
        when (customerType) {
            "General" -> "ราคาหน้าร้าน"
            "Regular" -> "ลูกค้าประจำ"
            "Wholesaler" -> "ราคาขายส่ง"
            else -> "ไม่มีราคา"
        }

    fun getCustomerTypeDisplay(): String {
        val customerType = customerTypes.firstOrNull { it.type == this.customerType }
        return customerType?.name ?: "-"
    }
}

data class ProductUnit(
    val id: String,
    val productId: String,
    var unit: String,
    var costPrice: Double,
    var size: Int,
    var barcode: String,
    var volume: Double,
    var volumeUnit: String,
)

data class ItemType(
    val name: String,
    val type: String,
)

val customerTypes =
    listOf(
        ItemType(name = "หน้าร้าน", type = "General"),
        ItemType(name = "ลูกค้าประจำ", type = "Regular"),
        ItemType(name = "ขายส่ง", type = "Wholesaler"),
    )

const val PRICE_TYPE_STOCK = "Stock"
