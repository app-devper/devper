package com.devper.app.core.data.repositories

import com.devper.app.core.data.remote.KtorUmNetwork
import com.devper.app.core.data.settings.MockSettings
import com.devper.app.core.domain.model.login.Login
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.model.login.System
import com.devper.app.core.network.config.MockNetworkConfig
import com.devper.app.core.network.di.MockHttpModule
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRepositoryImplTest {

    private lateinit var mockHttpModule: MockHttpModule
    private lateinit var umNetwork: KtorUmNetwork
    private lateinit var mockSettings: MockSettings
    private lateinit var loginRepository: LoginRepositoryImpl

    @BeforeTest
    fun setup() {
        val mockNetworkConfig = MockNetworkConfig()
        mockHttpModule = MockHttpModule(mockNetworkConfig)

        // Setup default mock responses
        setupMockResponses()

        umNetwork = KtorUmNetwork(httpModule = mockHttpModule)
        mockSettings = MockSettings()
        loginRepository = LoginRepositoryImpl(umNetwork, mockSettings)
    }

    @AfterTest
    fun tearDown() {
        mockSettings.clear()
        mockHttpModule.clearMockResponses()
    }

    private fun setupMockResponses() {
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/login",
            content = """{"accessToken": "mock-login-token"}""",
            status = HttpStatusCode.OK
        )

        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/keep-alive",
            content = """{"accessToken": "mock-keepalive-token"}""",
            status = HttpStatusCode.OK
        )

        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/system",
            content = """{
                "id": "mock-sys-id",
                "clientId": "mock-client-id",
                "systemName": "Mock System",
                "systemCode": "MOCK",
                "host": "https://mock.api.com"
            }""",
            status = HttpStatusCode.OK
        )

        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/logout",
            content = "",
            status = HttpStatusCode.NoContent
        )
    }

    // =================================
    // LOGIN TESTS
    // =================================

    @Test
    fun `should successfully login with valid credentials`() = runTest {
        // Given
        val loginParam = LoginParam(
            username = "testuser",
            password = "testpass",
            system = "testsystem"
        )

        // When
        val result = loginRepository.login(loginParam)

        // Then
        assertEquals(Login(accessToken = "mock-login-token"), result)
    }

    @Test
    fun `should handle login network error`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/login",
            content = """{"error": "Invalid credentials"}""",
            status = HttpStatusCode.Unauthorized
        )

        val loginParam = LoginParam("user", "wrongpass", "system")

        // When/Then
        assertFailsWith<Exception> {
            loginRepository.login(loginParam)
        }
    }

    @Test
    fun `should handle login server error`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/login",
            content = """{"error": "Internal server error"}""",
            status = HttpStatusCode.InternalServerError
        )

        val loginParam = LoginParam("user", "pass", "system")

        // When/Then
        assertFailsWith<Exception> {
            loginRepository.login(loginParam)
        }
    }

    // =================================
    // KEEP ALIVE TESTS
    // =================================

    @Test
    fun `should successfully refresh token with keep alive`() = runTest {
        // When
        val result = loginRepository.keepAlive()

        // Then
        assertEquals(Login(accessToken = "mock-keepalive-token"), result)
    }

    @Test
    fun `should handle keep alive network error`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/keep-alive",
            content = """{"error": "Token expired"}""",
            status = HttpStatusCode.Unauthorized
        )

        // When/Then
        assertFailsWith<Exception> {
            loginRepository.keepAlive()
        }
    }

    // =================================
    // GET SYSTEM TESTS
    // =================================

    @Test
    fun `should successfully get system information`() = runTest {
        // When
        val result = loginRepository.getSystem()

        // Then
        val expectedSystem = System(
            id = "mock-sys-id",
            clientId = "mock-client-id",
            systemName = "Mock System",
            systemCode = "MOCK",
            host = "https://mock.api.com"
        )
        assertEquals(expectedSystem, result)
    }

    @Test
    fun `should handle get system network error`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/system",
            content = """{"error": "System not found"}""",
            status = HttpStatusCode.NotFound
        )

        // When/Then
        assertFailsWith<Exception> {
            loginRepository.getSystem()
        }
    }

    // =================================
    // LOGOUT TESTS
    // =================================

    @Test
    fun `should successfully logout`() = runTest {
        // When - Should not throw exception
        loginRepository.logout()

        // Then - Test passes if no exception is thrown
        assertTrue(true)
    }

    @Test
    fun `should handle logout network error`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/auth/logout",
            content = """{"error": "Logout failed"}""",
            status = HttpStatusCode.InternalServerError
        )

        // When/Then
        assertFailsWith<Exception> {
            loginRepository.logout()
        }
    }

    // =================================
    // ACCESS TOKEN TESTS
    // =================================

    @Test
    fun `should get access token from settings`() = runTest {
        // Given
        val expectedToken = "stored-access-token"
        mockSettings.putString("access_token", expectedToken)

        // When
        val result = loginRepository.getAccessToken()

        // Then
        assertEquals(expectedToken, result)
    }

    @Test
    fun `should return empty string when access token not found`() = runTest {
        // Given - no token stored

        // When
        val result = loginRepository.getAccessToken()

        // Then
        assertEquals("", result)
    }

    @Test
    fun `should set access token in settings`() = runTest {
        // Given
        val tokenToStore = "new-access-token"

        // When
        loginRepository.setAccessToken(tokenToStore)

        // Then
        assertEquals(tokenToStore, mockSettings.getString("access_token", ""))
    }

    @Test
    fun `should overwrite existing access token in settings`() = runTest {
        // Given
        mockSettings.putString("access_token", "old-token")
        val newToken = "new-token"

        // When
        loginRepository.setAccessToken(newToken)

        // Then
        assertEquals(newToken, mockSettings.getString("access_token", ""))
    }

    // =================================
    // INTEGRATION TESTS
    // =================================

    @Test
    fun `should handle complete login workflow`() = runTest {
        // Given
        val loginParam = LoginParam("integrationuser", "integrationpass", "integration")

        // When - Complete login workflow
        val loginResult = loginRepository.login(loginParam)
        loginRepository.setAccessToken(loginResult.accessToken)
        val storedToken = loginRepository.getAccessToken()
        val keepAliveResult = loginRepository.keepAlive()
        val systemResult = loginRepository.getSystem()

        // Then
        assertEquals("mock-login-token", loginResult.accessToken)
        assertEquals("mock-login-token", storedToken)
        assertEquals("mock-keepalive-token", keepAliveResult.accessToken)
        assertEquals("mock-sys-id", systemResult.id)
    }

    @Test
    fun `should handle token refresh workflow`() = runTest {
        // Given
        val initialToken = "initial-token"
        loginRepository.setAccessToken(initialToken)

        // When
        val keepAliveResult = loginRepository.keepAlive()
        loginRepository.setAccessToken(keepAliveResult.accessToken)
        val newStoredToken = loginRepository.getAccessToken()

        // Then
        assertEquals("mock-keepalive-token", keepAliveResult.accessToken)
        assertEquals("mock-keepalive-token", newStoredToken)
    }

    // =================================
    // EDGE CASE TESTS
    // =================================

    @Test
    fun `should handle multiple consecutive operations`() = runTest {
        // Given
        val tokens = listOf("token1", "token2", "token3", "token4", "token5")

        // When - Multiple set/get operations
        tokens.forEach { token ->
            loginRepository.setAccessToken(token)
            assertEquals(token, loginRepository.getAccessToken())
        }

        // Then
        assertEquals("token5", loginRepository.getAccessToken())
    }
}
