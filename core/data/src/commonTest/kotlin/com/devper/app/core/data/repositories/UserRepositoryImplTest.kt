package com.devper.app.core.data.repositories

import com.devper.app.core.data.remote.KtorUmNetwork
import com.devper.app.core.domain.model.user.User
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
class UserRepositoryImplTest {

    private lateinit var mockHttpModule: MockHttpModule
    private lateinit var umNetwork: KtorUmNetwork
    private lateinit var userRepository: UserRepositoryImpl

    @BeforeTest
    fun setup() {
        val mockNetworkConfig = MockNetworkConfig()
        mockHttpModule = MockHttpModule(mockNetworkConfig)

        // Setup default mock responses
        setupMockResponses()

        umNetwork = KtorUmNetwork(httpModule = mockHttpModule)
        userRepository = UserRepositoryImpl(umNetwork)
    }

    @AfterTest
    fun tearDown() {
        mockHttpModule.clearMockResponses()
    }

    private fun setupMockResponses() {
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "mock-user-id",
                "firstName": "Mock",
                "lastName": "User",
                "username": "mockuser",
                "status": "ACTIVE",
                "role": "USER",
                "phone": "+1234567890",
                "email": "mock@example.com",
                "createdBy": "system",
                "createdDate": "2024-01-01T00:00:00Z",
                "updatedBy": "system",
                "updatedDate": "2024-01-01T00:00:00Z"
            }""",
            status = HttpStatusCode.OK
        )
    }

    // =================================
    // GET USER INFO TESTS
    // =================================

    @Test
    fun `should successfully get user info`() = runTest {
        // When
        val result = userRepository.getUserInfo()

        // Then
        val expectedUser = User(
            id = "mock-user-id",
            firstName = "Mock",
            lastName = "User",
            username = "mockuser",
            status = "ACTIVE",
            role = "USER",
            phone = "+1234567890",
            email = "mock@example.com",
            createdBy = "system",
            createdDate = "2024-01-01T00:00:00Z",
            updatedBy = "system",
            updatedDate = "2024-01-01T00:00:00Z"
        )
        assertEquals(expectedUser, result)
    }

    @Test
    fun `should handle network error when getting user info`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "User not found"}""",
            status = HttpStatusCode.NotFound
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle unauthorized error when getting user info`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "Invalid token"}""",
            status = HttpStatusCode.Unauthorized
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle server error when getting user info`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "Internal server error"}""",
            status = HttpStatusCode.InternalServerError
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle forbidden error when getting user info`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "Access denied"}""",
            status = HttpStatusCode.Forbidden
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle service unavailable error when getting user info`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "Service temporarily unavailable"}""",
            status = HttpStatusCode.ServiceUnavailable
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    // =================================
    // DATA MAPPING TESTS
    // =================================

    @Test
    fun `should correctly map user response with all fields`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "test-user-123",
                "firstName": "John",
                "lastName": "Doe",
                "username": "johndoe",
                "status": "ACTIVE",
                "role": "ADMIN",
                "phone": "+1-555-123-4567",
                "email": "john.doe@company.com",
                "createdBy": "admin",
                "createdDate": "2023-01-15T10:30:00Z",
                "updatedBy": "admin",
                "updatedDate": "2024-01-15T15:45:30Z"
            }""",
            status = HttpStatusCode.OK
        )

        // When
        val result = userRepository.getUserInfo()

        // Then
        assertEquals("test-user-123", result.id)
        assertEquals("John", result.firstName)
        assertEquals("Doe", result.lastName)
        assertEquals("johndoe", result.username)
        assertEquals("ACTIVE", result.status)
        assertEquals("ADMIN", result.role)
        assertEquals("+1-555-123-4567", result.phone)
        assertEquals("john.doe@company.com", result.email)
        assertEquals("admin", result.createdBy)
        assertEquals("2023-01-15T10:30:00Z", result.createdDate)
        assertEquals("admin", result.updatedBy)
        assertEquals("2024-01-15T15:45:30Z", result.updatedDate)
    }

    @Test
    fun `should handle user response with inactive status`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "inactive-user",
                "firstName": "Inactive",
                "lastName": "User",
                "username": "inactiveuser",
                "status": "INACTIVE",
                "role": "USER",
                "phone": "",
                "email": "inactive@company.com",
                "createdBy": "system",
                "createdDate": "2023-01-01T00:00:00Z",
                "updatedBy": "system",
                "updatedDate": "2023-01-01T00:00:00Z"
            }""",
            status = HttpStatusCode.OK
        )

        // When
        val result = userRepository.getUserInfo()

        // Then
        assertEquals("INACTIVE", result.status)
        assertEquals("", result.phone) // Empty phone should be handled
    }

    @Test
    fun `should handle user response with different roles`() = runTest {
        // Given
        val roles = listOf("USER", "ADMIN", "MANAGER", "VIEWER")

        roles.forEach { role ->
            mockHttpModule.clearMockResponses()
            mockHttpModule.addMockResponse(
                url = "/api/um/v1/user/info",
                content = """{
                    "id": "role-test-user",
                    "firstName": "Role",
                    "lastName": "Test",
                    "username": "roletest",
                    "status": "ACTIVE",
                    "role": "$role",
                    "phone": "+1234567890",
                    "email": "role@test.com",
                    "createdBy": "system",
                    "createdDate": "2024-01-01T00:00:00Z",
                    "updatedBy": "system",
                    "updatedDate": "2024-01-01T00:00:00Z"
                }""",
                status = HttpStatusCode.OK
            )

            // When
            val result = userRepository.getUserInfo()

            // Then
            assertEquals(role, result.role)
        }
    }

    // =================================
    // EDGE CASE TESTS
    // =================================

    @Test
    fun `should handle malformed JSON response`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"malformed": json}""",
            status = HttpStatusCode.OK
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle empty response body`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = "",
            status = HttpStatusCode.OK
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle response with missing required fields`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "partial-user",
                "firstName": "Partial"
            }""",
            status = HttpStatusCode.OK
        )

        // When/Then
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    // =================================
    // NETWORK STATUS CODE TESTS
    // =================================

    @Test
    fun `should handle all common HTTP error codes`() = runTest {
        val errorCodes = listOf(
            HttpStatusCode.BadRequest to "Bad request",
            HttpStatusCode.Unauthorized to "Unauthorized access",
            HttpStatusCode.Forbidden to "Access forbidden",
            HttpStatusCode.NotFound to "User not found",
            HttpStatusCode.MethodNotAllowed to "Method not allowed",
            HttpStatusCode.RequestTimeout to "Request timeout",
            HttpStatusCode.InternalServerError to "Internal server error",
            HttpStatusCode.BadGateway to "Bad gateway",
            HttpStatusCode.ServiceUnavailable to "Service unavailable",
            HttpStatusCode.GatewayTimeout to "Gateway timeout"
        )

        errorCodes.forEach { (statusCode, errorMessage) ->
            mockHttpModule.clearMockResponses()
            mockHttpModule.addMockResponse(
                url = "/api/um/v1/user/info",
                content = """{"error": "$errorMessage"}""",
                status = statusCode
            )

            // When/Then
            assertFailsWith<Exception> {
                userRepository.getUserInfo()
            }
        }
    }

    // =================================
    // INTEGRATION TESTS
    // =================================

    @Test
    fun `should successfully complete multiple getUserInfo calls`() = runTest {
        // When - Multiple calls should work consistently
        val result1 = userRepository.getUserInfo()
        val result2 = userRepository.getUserInfo()
        val result3 = userRepository.getUserInfo()

        // Then - All results should be identical
        assertEquals(result1, result2)
        assertEquals(result2, result3)
        assertEquals("mock-user-id", result1.id)
        assertEquals("Mock", result1.firstName)
        assertEquals("User", result1.lastName)
    }

    @Test
    fun `should handle rapid successive calls`() = runTest {
        // Given
        val calls = 10
        val results = mutableListOf<User>()

        // When - Make rapid successive calls
        repeat(calls) {
            results.add(userRepository.getUserInfo())
        }

        // Then - All results should be consistent
        assertTrue(results.isNotEmpty())
        assertEquals(calls, results.size)
        results.forEach { user ->
            assertEquals("mock-user-id", user.id)
            assertEquals("Mock", user.firstName)
        }
    }

    // =================================
    // PERFORMANCE TESTS
    // =================================

    @Test
    fun `should handle user info with large data fields`() = runTest {
        // Given
        val largeString = "x".repeat(1000) // Large string to test handling
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "large-data-user",
                "firstName": "$largeString",
                "lastName": "User",
                "username": "largeuser",
                "status": "ACTIVE",
                "role": "USER",
                "phone": "+1234567890",
                "email": "large@test.com",
                "createdBy": "system",
                "createdDate": "2024-01-01T00:00:00Z",
                "updatedBy": "system",
                "updatedDate": "2024-01-01T00:00:00Z"
            }""",
            status = HttpStatusCode.OK
        )

        // When
        val result = userRepository.getUserInfo()

        // Then
        assertEquals("large-data-user", result.id)
        assertEquals(largeString, result.firstName)
        assertEquals(1000, result.firstName.length)
    }

    @Test
    fun `should handle network exception properly`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{"error": "Network timeout"}""",
            status = HttpStatusCode.RequestTimeout
        )

        // When/Then - Should throw HttpException after conversion
        assertFailsWith<Exception> {
            userRepository.getUserInfo()
        }
    }

    @Test
    fun `should handle user with empty optional fields`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/um/v1/user/info",
            content = """{
                "id": "minimal-user",
                "firstName": "Minimal",
                "lastName": "User",
                "username": "minimaluser",
                "status": "ACTIVE",
                "role": "USER",
                "phone": "",
                "email": "",
                "createdBy": "",
                "createdDate": "2024-01-01T00:00:00Z",
                "updatedBy": "",
                "updatedDate": "2024-01-01T00:00:00Z"
            }""",
            status = HttpStatusCode.OK
        )

        // When
        val result = userRepository.getUserInfo()

        // Then
        assertEquals("minimal-user", result.id)
        assertEquals("Minimal", result.firstName)
        assertEquals("User", result.lastName)
        assertEquals("", result.phone)
        assertEquals("", result.email)
        assertEquals("", result.createdBy)
        assertEquals("", result.updatedBy)
    }
}
