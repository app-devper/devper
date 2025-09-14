package com.devper.app.core.domain.usecases

import com.devper.app.core.domain.di.domainModule
import com.devper.app.core.domain.di.testModule
import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.System
import com.devper.app.core.domain.provider.HostProvider
import com.devper.app.core.domain.provider.MockHostProvider
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
class KeepAliveUseCaseTest : KoinTest {

    private val keepAliveUseCase: KeepAliveUseCase by inject()
    private val mockLoginRepository: LoginRepository by inject()
    private val mockSessionProvider: SessionProvider by inject()
    private val mockHostProvider: HostProvider by inject()

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
    fun `should fail when access token is empty`() = runTest {
        // Given
        val loginRepository = mockLoginRepository as MockLoginRepository
        loginRepository.accessTokenResponse = "" // Empty access token

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("Access token is empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `should successfully keep alive and update all providers`() = runTest {
        // Given
        val initialAccessToken = "initial-token"
        val newAccessToken = "new-access-token"
        val expectedLogin = Login(accessToken = newAccessToken)
        val expectedSystem = System(
            id = "sys-123",
            clientId = "client-456",
            systemName = "Test System",
            systemCode = "TEST",
            host = "https://test.api.com"
        )

        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider
        val hostProvider = mockHostProvider as MockHostProvider

        // Setup mock responses
        loginRepository.accessTokenResponse = initialAccessToken
        loginRepository.keepAliveResponse = expectedLogin
        loginRepository.systemResponse = expectedSystem

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedLogin, result.getOrNull())

        // Verify new access token was set in both repository and session
        assertEquals(newAccessToken, loginRepository.lastSetAccessToken)
        assertEquals(newAccessToken, sessionProvider.lastSetAccessToken)

        // Verify system was set in session provider (via clientId)
        assertEquals("client-456", sessionProvider.lastSetClientId)

        // Verify system was set in host provider
        assertEquals("https://test.api.com", hostProvider.lastUpdatedHost)
    }

    @Test
    fun `should handle get access token failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Failed to get access token")
        val loginRepository = mockLoginRepository as MockLoginRepository
        loginRepository.getAccessTokenException = expectedException

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle keep alive repository failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Keep alive failed")
        val loginRepository = mockLoginRepository as MockLoginRepository
        loginRepository.accessTokenResponse = "valid-token"
        loginRepository.keepAliveException = expectedException

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle set access token in repository failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Failed to set access token in repository")
        val expectedLogin = Login(accessToken = "new-token")
        val loginRepository = mockLoginRepository as MockLoginRepository

        loginRepository.accessTokenResponse = "valid-token"
        loginRepository.keepAliveResponse = expectedLogin
        loginRepository.setAccessTokenException = expectedException

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle get system failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Failed to get system")
        val expectedLogin = Login(accessToken = "new-token")
        val loginRepository = mockLoginRepository as MockLoginRepository

        loginRepository.accessTokenResponse = "valid-token"
        loginRepository.keepAliveResponse = expectedLogin
        loginRepository.getSystemException = expectedException

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle session provider failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Session provider failed")
        val expectedLogin = Login(accessToken = "new-token")
        val expectedSystem = System("sys-1", "client-1", "System", "SYS", "host")
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider

        loginRepository.accessTokenResponse = "valid-token"
        loginRepository.keepAliveResponse = expectedLogin
        loginRepository.systemResponse = expectedSystem
        sessionProvider.setAccessTokenException = expectedException

        // When
        val result = keepAliveUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

}
