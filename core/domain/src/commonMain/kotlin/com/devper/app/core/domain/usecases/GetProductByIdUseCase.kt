package com.devper.app.core.domain.usecases

import com.devper.app.core.common.Result
import com.devper.app.core.common.UseCase
import com.devper.app.core.common.thread.Dispatcher
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.ProductRepository

class GetProductByIdUseCase(
    dispatcher: Dispatcher,
    private val productRepo: ProductRepository,
    private val session: SessionProvider,
) : UseCase<String, Result<Product>>(dispatcher.io()) {

    override suspend fun execute(param: String): Result<Product> {
        return try {
            productRepo.getProductById(param).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}