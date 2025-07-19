package com.devper.app.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(param: P): R {
        return try {
            withContext(dispatcher) {
                execute(param)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): R
}
