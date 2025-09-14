package com.devper.app.core.domain.usecases

import com.devper.app.core.domain.di.domainModule
import com.devper.app.core.domain.di.testModule
import com.devper.app.core.domain.model.user.User
import com.devper.app.core.domain.provider.SessionProvider
import com.devper.app.core.domain.repositories.MockUserRepository
import com.devper.app.core.domain.repositories.UserRepository
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
class GetUserInfoUseCaseTest : KoinTest {

    private val getUserInfoUseCase: GetUserInfoUseCase by inject()
    private val mockUserRepository: UserRepository by inject()
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
    fun `should successfully get user info`() = runTest {
        // Given
        val expectedUser = User(
            id = "user-123",
            firstName = "John",
            lastName = "Doe",
            username = "johndoe",
            status = "ACTIVE",
            role = "USER",
            phone = "+1234567890",
            email = "john.doe@example.com",
            createdBy = "admin",
            createdDate = "2024-01-01T00:00:00Z",
            updatedBy = "admin",
            updatedDate = "2024-01-01T00:00:00Z"
        )

        val userRepository = mockUserRepository as MockUserRepository
        userRepository.userInfoResponse = expectedUser

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())

        // Verify repository interactions
        assertTrue(userRepository.getUserInfoCalled)
    }

    @Test
    fun `should handle repository failure`() = runTest {
        // Given
        val expectedException = RuntimeException("Failed to get user info")
        val userRepository = mockUserRepository as MockUserRepository
        userRepository.getUserInfoException = expectedException

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())

        // Verify repository was called
        assertTrue(userRepository.getUserInfoCalled)
    }

    @Test
    fun `should handle network timeout exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Connection timeout")
        val userRepository = mockUserRepository as MockUserRepository
        userRepository.getUserInfoException = expectedException

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        assertTrue(userRepository.getUserInfoCalled)
    }

    @Test
    fun `should handle unauthorized exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Unauthorized access")
        val userRepository = mockUserRepository as MockUserRepository
        userRepository.getUserInfoException = expectedException

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        assertTrue(userRepository.getUserInfoCalled)
    }

    @Test
    fun `should return user with all required fields populated`() = runTest {
        // Given
        val expectedUser = User(
            id = "test-id",
            firstName = "Test",
            lastName = "User",
            username = "testuser",
            status = "INACTIVE",
            role = "ADMIN",
            phone = "+9876543210",
            email = "test@example.com",
            createdBy = "system",
            createdDate = "2023-12-31T23:59:59Z",
            updatedBy = "system",
            updatedDate = "2024-12-31T23:59:59Z"
        )

        val userRepository = mockUserRepository as MockUserRepository
        userRepository.userInfoResponse = expectedUser

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isSuccess)
        val user = result.getOrNull()!!

        // Verify all fields are properly set
        assertEquals("test-id", user.id)
        assertEquals("Test", user.firstName)
        assertEquals("User", user.lastName)
        assertEquals("testuser", user.username)
        assertEquals("INACTIVE", user.status)
        assertEquals("ADMIN", user.role)
        assertEquals("+9876543210", user.phone)
        assertEquals("test@example.com", user.email)
        assertEquals("system", user.createdBy)
        assertEquals("2023-12-31T23:59:59Z", user.createdDate)
        assertEquals("system", user.updatedBy)
        assertEquals("2024-12-31T23:59:59Z", user.updatedDate)
    }

    @Test
    fun `should handle service unavailable exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Service temporarily unavailable")
        val userRepository = mockUserRepository as MockUserRepository
        userRepository.getUserInfoException = expectedException

        // When
        val result = getUserInfoUseCase.invoke(Unit)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        assertTrue(userRepository.getUserInfoCalled)
    }
}
