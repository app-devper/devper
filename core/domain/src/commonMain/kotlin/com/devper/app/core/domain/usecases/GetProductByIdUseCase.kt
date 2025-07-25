package com.devper.app.core.domain.usecases

import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.ProductRepository

class GetProductByIdUseCase(
    dispatcher: Dispatcher,
    private val productRepo: ProductRepository,
    private val session: SessionProvider,
) : UseCase<String, Product>(dispatcher.io()) {

    override suspend fun execute(param: String): Product {
        return productRepo.getProductById(param)
    }
}