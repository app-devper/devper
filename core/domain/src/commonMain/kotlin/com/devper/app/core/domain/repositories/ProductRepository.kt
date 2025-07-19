package com.devper.app.core.domain.repositories

import com.devper.app.core.domain.model.product.CreateProductParam
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.model.product.ProductPrice
import com.devper.app.core.domain.model.product.ProductPriceParam
import com.devper.app.core.domain.model.product.ProductStock
import com.devper.app.core.domain.model.product.ProductStockParam
import com.devper.app.core.domain.model.product.ProductUnit
import com.devper.app.core.domain.model.product.ProductUnitParam
import com.devper.app.core.domain.model.product.UpdateProductStockQuantityParam
import com.devper.app.core.domain.model.product.UpdateProductStockSequenceParam

interface ProductRepository {

    //    suspend fun getProductByBarcode(barcode: String): Product?
//
    suspend fun getProductById(productId: String): Product
//
//    suspend fun getLocalProductById(productId: String): Product?
//
//    suspend fun addProductReceive(param: ProductParam): Product
//
//    suspend fun addProduct(param: CreateProductParam): Product
//
//    suspend fun updateProductById(productId: String, param: ProductParam): Product

    suspend fun getProducts(): List<Product>

    suspend fun getLocalProducts(): List<Product>
//
//    suspend fun removeProductById(productId: String): Product
//
//    suspend fun addProductPrice(param: ProductPriceParam): ProductPrice
//
//    suspend fun updateProductPriceById(id: String, param: ProductPriceParam): ProductPrice
//
//    suspend fun removeProductPriceById(id: String): ProductPrice
//
//    suspend fun getProductPricesByProductId(productId: String): List<ProductPrice>
//
//    suspend fun addProductUnit(param: ProductUnitParam): ProductUnit
//
//    suspend fun updateProductUnitById(id: String, param: ProductUnitParam): ProductUnit
//
//    suspend fun removeProductUnitById(id: String): ProductUnit
//
//    suspend fun getProductUnitsByProductId(productId: String): List<ProductUnit>
//
//    suspend fun addProductStock(param: ProductStockParam): ProductStock
//
//    suspend fun updateProductStockById(id: String, param: ProductStockParam): ProductStock
//
//    suspend fun removeProductStockById(id: String): ProductStock
//
//    suspend fun getProductStocksByProductId(productId: String): List<ProductStock>
//
//    suspend fun updateProductStockQuantityById(id: String, param: UpdateProductStockQuantityParam): ProductStock
//
//    suspend fun updateProductStockSequence(param: UpdateProductStockSequenceParam): List<ProductStock>
//
//    suspend fun updateProductStock(element: ProductStock): ProductStockParam
}
