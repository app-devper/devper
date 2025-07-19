package com.devper.app.core.domain.usecases

import com.devper.app.core.common.Result
import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.ProductRepository

class GetProductsUseCase(
    dispatcher: Dispatcher,
    private val productRepo: ProductRepository,
    private val session: SessionProvider,
) : UseCase<Unit, Result<List<Product>>>(dispatcher.io()) {

    override suspend fun execute(param: Unit): Result<List<Product>> {
        return try {
            productRepo.getLocalProducts().let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}


