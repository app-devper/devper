package com.devper.app.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.Result

abstract class UseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(param: P): Result<R> =
        withContext(dispatcher) {
            try {
                Result.success(execute(param))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): R
}
