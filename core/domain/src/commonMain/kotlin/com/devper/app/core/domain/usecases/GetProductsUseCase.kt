package com.devper.app.core.domain.usecases

import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.ProductRepository

class GetProductsUseCase(
    dispatcher: Dispatcher,
    private val productRepo: ProductRepository,
    private val session: SessionProvider,
) : UseCase<Unit, List<Product>>(dispatcher.io()) {
    override suspend fun execute(param: Unit): List<Product> = productRepo.getLocalProducts()
}
