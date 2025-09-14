package com.devper.app.core.domain.usecases

import com.devper.app.core.domain.di.domainModule
import com.devper.app.core.domain.di.testModule
import com.devper.app.core.domain.provider.MockSessionProvider
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository
import com.devper.app.core.domain.repositories.MockLoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LogoutUseCaseTest : KoinTest {

    private val logoutUseCase: LogoutUseCase by inject()
    private val mockLoginRepository: LoginRepository by inject()
    private val mockSessionProvider: SessionProvider by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(testModule, domainModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should successfully logout and clear session`() = runTest {
        // Given - no specific setup needed for successful logout
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider

        // When
        val result = logoutUseCase.invoke(Unit)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())

        // Verify repository interactions
        assertTrue(loginRepository.logoutCalled)

        // Verify session provider interactions
        assertTrue(sessionProvider.clearCalled)
    }

    @Test
    fun `should handle logout repository failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Logout failed")
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider
        loginRepository.logoutException = expectedException

        // When
        val result = logoutUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())

        // Verify repository was called
        assertTrue(loginRepository.logoutCalled)

        // Verify session was not cleared due to exception
        assertTrue(!sessionProvider.clearCalled)
    }

    @Test
    fun `should handle session clear failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Clear session failed")
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider
        sessionProvider.clearException = expectedException

        // When
        val result = logoutUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())

        // Verify repository was called
        assertTrue(loginRepository.logoutCalled)

        // Verify session clear was attempted
        assertTrue(sessionProvider.clearCalled)
    }

    @Test
    fun `should execute logout operations in correct order`() = runTest {
        // Given
        val operationOrder = mutableListOf<String>()
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider

        loginRepository.onLogout = { operationOrder.add("logout") }
        sessionProvider.onClear = { operationOrder.add("clear") }

        // When
        val result = logoutUseCase.invoke(Unit)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(listOf("logout", "clear"), operationOrder)
    }
}
