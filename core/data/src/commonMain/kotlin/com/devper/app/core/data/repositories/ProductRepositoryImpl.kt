package com.devper.app.core.data.repositories

import com.devper.app.core.data.mapper.toHttpException
import com.devper.app.core.data.mapper.toProducts
import com.devper.app.core.data.remote.KtorPosNetwork
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.repositories.ProductRepository
import com.devper.app.core.network.exception.NetworkException

class ProductRepositoryImpl(
    private val posNetwork: KtorPosNetwork
) : ProductRepository {

    private var products: List<Product> = listOf()


    override suspend fun getProducts(): List<Product> {
        try {
            posNetwork.getProducts().let {
                products = it.toProducts()
                return products
            }
        } catch (e: NetworkException) {
            throw e.toHttpException()
        }
    }

    override suspend fun getLocalProducts(): List<Product> {
        return products.ifEmpty {
            getProducts()
        }
    }

    override suspend fun getProductById(productId: String): Product {
        return products.firstOrNull { it.id == productId } ?: throw Exception("Product not found")
    }

//    override suspend fun getProductById(productId: String): Product {
//        val mapper = ProductMapper()
//        val response = posNetwork.getProductById(productId)
//        return if (response.isSuccessful) {
//            mapper.toProductDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun addProductReceive(param: ProductParam): Product {
//        val mapper = ProductMapper()
//        val request = mapper.toProductRequest(param)
//        val response = posNetwork.createProductReceive(request)
//        return if (response.isSuccessful) {
//            mapper.toProductDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun addProduct(param: CreateProductParam): Product {
//        val mapper = ProductMapper()
//        val request = mapper.toCreateProductRequest(param)
//        val response = posNetwork.createProduct(request)
//        return if (response.isSuccessful) {
//            val product = mapper.toProductDomain(JSONObject(response.body))
//            products = products + product
//            product
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductById(productId: String, param: ProductParam): Product {
//        val mapper = ProductMapper()
//        val request = mapper.toProductRequest(param)
//        val response = posNetwork.updateProductById(productId, request)
//        return if (response.isSuccessful) {
//            val product = mapper.toProductDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == product.id) product else it
//            }
//            product
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun removeProductById(productId: String): Product {
//        val mapper = ProductMapper()
//        val response = posNetwork.removeProductById(productId)
//        return if (response.isSuccessful) {
//            val product = mapper.toProductDomain(JSONObject(response.body))
//            products = products.filterNot { it.id == product.id }
//            product
//        } else {
//            throw HttpException(response)
//        }
//    }
//

//    override suspend fun getLocalProductById(productId: String): Product? {
//        return products.firstOrNull { it.id == productId }
//    }
//
//    override suspend fun getProductLotsExpired(): List<ProductLot> {
//        val mapper = ProductMapper()
//        val response = posNetwork.getProductLotsExpired()
//        return if (response.isSuccessful) {
//            mapper.toProductLotsDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun getProductLotByLotId(lotId: String): ProductLot {
//        val mapper = ProductMapper()
//        val response = posService.getProductLotById(lotId)
//        return if (response.isSuccessful) {
//            mapper.toProductLotDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductLotQuantityByLotId(lotId: String, param: UpdateProductLotQuantityParam): ProductLot {
//        val mapper = ProductMapper()
//        val request = mapper.toUpdateProductLotQuantityRequest(param)
//        val response = posNetwork.updateProductLotQuantityById(lotId, request)
//        return if (response.isSuccessful) {
//            mapper.toProductLotDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun getProductLots(param: GetLotsRangeParam): List<ProductLot> {
//        val mapper = ProductMapper()
//        val response = posService.getProductLots(param.startDate, param.endDate)
//        return if (response.isSuccessful) {
//            mapper.toProductLotsDomain(JSONObject(response.body))
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun addProductPrice(param: ProductPriceParam): ProductPrice {
//        val mapper = ProductMapper()
//        val response = posNetwork.addProductPrice(mapper.toProductPriceRequest(param))
//        return if (response.isSuccessful) {
//            val price = mapper.toProductPriceDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == price.productId) {
//                    it.copy(prices = it.prices + price)
//                } else {
//                    it
//                }
//            }
//            price
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductPriceById(id: String, param: ProductPriceParam): ProductPrice {
//        val mapper = ProductMapper()
//        val response = posNetwork.updateProductPriceById(id, mapper.toProductPriceRequest(param))
//        return if (response.isSuccessful) {
//            val price = mapper.toProductPriceDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == price.productId) {
//                    it.copy(prices = it.prices.map { p -> if (p.id == price.id) price else p })
//                } else {
//                    it
//                }
//            }
//            price
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun removeProductPriceById(id: String): ProductPrice {
//        val mapper = ProductMapper()
//        val response = posNetwork.removeProductPriceById(id)
//        return if (response.isSuccessful) {
//            val price = mapper.toProductPriceDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == price.productId) {
//                    it.copy(prices = it.prices.filterNot { p -> p.id == price.id })
//                } else {
//                    it
//                }
//            }
//            price
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun getProductPricesByProductId(productId: String): List<ProductPrice> {
//        val mapper = ProductMapper()
//        val response = posNetwork.getProductPricesByProductId(productId)
//        return if (response.isSuccessful) {
//            val prices = mapper.toProductPricesDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == productId) {
//                    it.copy(prices = prices)
//                } else {
//                    it
//                }
//            }
//            prices
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun addProductUnit(param: ProductUnitParam): ProductUnit {
//        val mapper = ProductMapper()
//        val response = posNetwork.addProductUnit(mapper.toProductUnitRequest(param))
//        return if (response.isSuccessful) {
//            val unit = mapper.toProductUnitDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == unit.productId) {
//                    it.copy(units = it.units + unit)
//                } else {
//                    it
//                }
//            }
//            unit
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductUnitById(id: String, param: ProductUnitParam): ProductUnit {
//        val mapper = ProductMapper()
//        val response = posService.updateProductUnitById(id, mapper.toProductUnitRequest(param))
//        return if (response.isSuccessful) {
//            val unit = mapper.toProductUnitDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == unit.productId) {
//                    it.copy(units = it.units.map { u -> if (u.id == unit.id) unit else u })
//                } else {
//                    it
//                }
//            }
//            unit
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun removeProductUnitById(id: String): ProductUnit {
//        val mapper = ProductMapper()
//        val response = posService.removeProductUnitById(id)
//        return if (response.isSuccessful) {
//            val unit = mapper.toProductUnitDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == unit.productId) {
//                    it.copy(units = it.units.filterNot { u -> u.id == unit.id })
//                } else {
//                    it
//                }
//            }
//            unit
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun getProductUnitsByProductId(productId: String): List<ProductUnit> {
//        val mapper = ProductMapper()
//        val response = posService.getProductUnitsByProductId(productId)
//        return if (response.isSuccessful) {
//            val units = mapper.toProductUnitsDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == productId) {
//                    it.copy(units = units)
//                } else {
//                    it
//                }
//            }
//            units
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun addProductStock(param: ProductStockParam): ProductStock {
//        val mapper = ProductMapper()
//        val response = posService.addProductStock(mapper.toProductStockRequest(param))
//        return if (response.isSuccessful) {
//            val stock = mapper.toProductStockDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == stock.productId) {
//                    it.copy(stocks = it.stocks + stock)
//                } else {
//                    it
//                }
//            }
//            stock
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductStockById(id: String, param: ProductStockParam): ProductStock {
//        val mapper = ProductMapper()
//        val response = posService.updateProductStockById(id, mapper.toProductStockRequest(param))
//        return if (response.isSuccessful) {
//            val stock = mapper.toProductStockDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == stock.productId) {
//                    it.copy(stocks = it.stocks.map { s -> if (s.id == stock.id) stock else s })
//                } else {
//                    it
//                }
//            }
//            stock
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun removeProductStockById(id: String): ProductStock {
//        val mapper = ProductMapper()
//        val response = posService.removeProductStockById(id)
//        return if (response.isSuccessful) {
//            val stock = mapper.toProductStockDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == stock.productId) {
//                    it.copy(stocks = it.stocks.filterNot { s -> s.id == stock.id })
//                } else {
//                    it
//                }
//            }
//            stock
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun getProductStocksByProductId(productId: String): List<ProductStock> {
//        val mapper = ProductMapper()
//        val response = posService.getProductStocksByProductId(productId)
//        return if (response.isSuccessful) {
//            val stocks = mapper.toProductStocksDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == productId) {
//                    it.copy(stocks = stocks)
//                } else {
//                    it
//                }
//            }
//            stocks
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductStockQuantityById(id: String, param: UpdateProductStockQuantityParam): ProductStock {
//        val mapper = ProductMapper()
//        val response = posService.updateProductStockQuantityById(id, mapper.toUpdateProductStockQuantityRequest(param))
//        return if (response.isSuccessful) {
//            val stock = mapper.toProductStockDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == stock.productId) {
//                    it.copy(stocks = it.stocks.map { s -> if (s.id == stock.id) stock else s })
//                } else {
//                    it
//                }
//            }
//            stock
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductStockSequence(param: UpdateProductStockSequenceParam): List<ProductStock> {
//        val mapper = ProductMapper()
//        val response = posService.updateProductStockSequence(mapper.toUpdateProductStockSequenceRequest(param))
//        return if (response.isSuccessful) {
//            val stocks = mapper.toProductStocksDomain(JSONObject(response.body))
//            products = products.map {
//                if (it.id == param.productId) {
//                    it.copy(stocks = stocks)
//                } else {
//                    it
//                }
//            }
//            stocks
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun updateProductStock(stock: ProductStock) {
//        products = products.map { product ->
//            if (product.id == stock.productId) {
//                product.copy(stocks = product.stocks.map { s ->
//                    if (s.id == stock.id) stock else s
//                })
//            } else {
//                product
//            }
//        }
//    }
}
