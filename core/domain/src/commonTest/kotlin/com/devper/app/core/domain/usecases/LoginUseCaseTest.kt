package com.devper.app.core.domain.usecases

import com.devper.app.core.domain.di.domainModule
import com.devper.app.core.domain.di.testModule
import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System
import com.devper.app.core.domain.provider.HostProvider
import com.devper.app.core.domain.provider.MockHostProvider
import com.devper.app.core.domain.provider.MockSessionProvider
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.LoginRepository
import com.devper.app.core.domain.repositories.MockLoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class LoginUseCaseTest : KoinTest {

    private val loginUseCase: LoginUseCase by inject()
    private val mockLoginRepository: LoginRepository by inject()
    private val mockSessionProvider: SessionProvider by inject()
    private val mockHostProvider: HostProvider by inject()

    private val testDispatcher = StandardTestDispatcher()

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
    fun `should successfully login and set tokens and system`() = runTest {
        // Given
        val loginParam = LoginParam(
            username = "testuser",
            password = "testpass",
            system = "testsystem"
        )
        val expectedLogin = Login(accessToken = "test-access-token")
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

        loginRepository.loginResponse = expectedLogin
        loginRepository.systemResponse = expectedSystem

        // When
        val result = loginUseCase.invoke(loginParam)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedLogin, result.getOrNull())

        // Verify repository interactions
        assertEquals(loginParam, loginRepository.lastLoginParam)
        assertEquals("test-access-token", loginRepository.lastSetAccessToken)

        // Verify session provider interactions
        assertEquals("test-access-token", loginRepository.lastSetAccessToken)
        assertEquals("client-456", sessionProvider.lastSetClientId)

        // Verify host provider interactions
        assertEquals("https://test.api.com", hostProvider.lastUpdatedHost)
    }

    @Test
    fun `should handle login failure`() = runTest {
        // Given
        val loginParam = LoginParam("user", "pass", "system")
        val expectedException = RuntimeException("Login failed")
        val loginRepository = mockLoginRepository as MockLoginRepository
        loginRepository.loginException = expectedException

        // When
        val result = loginUseCase.invoke(loginParam)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle repository access token setting failure`() = runTest {
        // Given
        val loginParam = LoginParam("user", "pass", "system")
        val expectedLogin = Login(accessToken = "test-token")
        val expectedException = RuntimeException("Failed to set access token")
        val loginRepository = mockLoginRepository as MockLoginRepository

        loginRepository.loginResponse = expectedLogin
        loginRepository.setAccessTokenException = expectedException

        // When
        val result = loginUseCase.invoke(loginParam)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle get system failure`() = runTest {
        // Given
        val loginParam = LoginParam("user", "pass", "system")
        val expectedLogin = Login(accessToken = "test-token")
        val expectedException = RuntimeException("Failed to get system")
        val loginRepository = mockLoginRepository as MockLoginRepository

        loginRepository.loginResponse = expectedLogin
        loginRepository.getSystemException = expectedException

        // When
        val result = loginUseCase.invoke(loginParam)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `should handle session provider failure`() = runTest {
        // Given
        val loginParam = LoginParam("user", "pass", "system")
        val expectedLogin = Login(accessToken = "test-token")
        val expectedSystem = System("sys-1", "client-1", "System", "SYS", "host")
        val expectedException = RuntimeException("Session provider failed")
        val loginRepository = mockLoginRepository as MockLoginRepository
        val sessionProvider = mockSessionProvider as MockSessionProvider

        loginRepository.loginResponse = expectedLogin
        loginRepository.systemResponse = expectedSystem
        sessionProvider.setAccessTokenException = expectedException

        // When
        val result = loginUseCase.invoke(loginParam)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

}
