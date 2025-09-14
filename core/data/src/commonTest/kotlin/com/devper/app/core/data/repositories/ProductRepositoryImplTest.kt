package com.devper.app.core.data.repositories

import com.devper.app.core.data.remote.KtorPosNetwork
import com.devper.app.core.domain.model.product.Product
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
class ProductRepositoryImplTest {

    private lateinit var mockHttpModule: MockHttpModule
    private lateinit var posNetwork: KtorPosNetwork
    private lateinit var productRepository: ProductRepositoryImpl

    @BeforeTest
    fun setup() {
        val mockNetworkConfig = MockNetworkConfig()
        mockHttpModule = MockHttpModule(mockNetworkConfig)

        // Setup default mock responses
        setupMockResponses()

        posNetwork = KtorPosNetwork(httpModule = mockHttpModule)
        productRepository = ProductRepositoryImpl(posNetwork)
    }

    @AfterTest
    fun tearDown() {
        mockHttpModule.clearMockResponses()
    }

    private fun setupMockResponses() {
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """[
                {
                    "id": "product-1",
                    "name": "Test Product 1",
                    "nameEn": "Test Product 1 EN",
                    "description": "Test description 1",
                    "status": "ACTIVE",
                    "category": "Electronics",
                    "createdDate": "2024-01-01T00:00:00Z",
                    "units": [],
                    "prices": [],
                    "stocks": []
                },
                {
                    "id": "product-2",
                    "name": "Test Product 2",
                    "nameEn": "Test Product 2 EN",
                    "description": "Test description 2",
                    "status": "ACTIVE",
                    "category": "Books",
                    "createdDate": "2024-01-02T00:00:00Z",
                    "units": [],
                    "prices": [],
                    "stocks": []
                }
            ]""",
            status = HttpStatusCode.OK
        )
    }

    // =================================
    // GET PRODUCTS TESTS
    // =================================

    @Test
    fun `should successfully get products from network`() = runTest {
        // When
        val result = productRepository.getProducts()

        // Then
        assertEquals(2, result.size)
        assertEquals("product-1", result[0].id)
        assertEquals("Test Product 1", result[0].name)
        assertEquals("Test Product 1 EN", result[0].nameEn)
        assertEquals("Test description 1", result[0].description)
        assertEquals("ACTIVE", result[0].status)
        assertEquals("Electronics", result[0].category)
        assertEquals("2024-01-01T00:00:00Z", result[0].createdDate)

        assertEquals("product-2", result[1].id)
        assertEquals("Test Product 2", result[1].name)
        assertEquals("Books", result[1].category)
    }

    @Test
    fun `should handle network error when getting products`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """{"error": "Products not found"}""",
            status = HttpStatusCode.NotFound
        )

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getProducts()
        }
    }

    @Test
    fun `should handle server error when getting products`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """{"error": "Internal server error"}""",
            status = HttpStatusCode.InternalServerError
        )

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getProducts()
        }
    }

    @Test
    fun `should handle unauthorized error when getting products`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """{"error": "Unauthorized"}""",
            status = HttpStatusCode.Unauthorized
        )

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getProducts()
        }
    }

    @Test
    fun `should handle empty products list`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """[]""",
            status = HttpStatusCode.OK
        )

        // When
        val result = productRepository.getProducts()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should cache products in memory after network call`() = runTest {
        // When - First call should fetch from network
        val result1 = productRepository.getProducts()

        // Clear mock responses to ensure second call doesn't hit network
        mockHttpModule.clearMockResponses()

        // When - Second call should return cached data
        val result2 = productRepository.getProducts()

        // Then - Results should be identical and cached
        assertEquals(result1.size, result2.size)
        assertEquals(result1[0].id, result2[0].id)
        assertEquals(result1[0].name, result2[0].name)
    }

    // =================================
    // GET LOCAL PRODUCTS TESTS
    // =================================

    @Test
    fun `should return cached products when available`() = runTest {
        // Given - First populate the cache
        val networkProducts = productRepository.getProducts()

        // When - Get local products
        val localProducts = productRepository.getLocalProducts()

        // Then - Should return the same products from cache
        assertEquals(networkProducts.size, localProducts.size)
        assertEquals(networkProducts[0].id, localProducts[0].id)
        assertEquals(networkProducts[0].name, localProducts[0].name)
        assertEquals(networkProducts[1].id, localProducts[1].id)
        assertEquals(networkProducts[1].name, localProducts[1].name)
    }

    @Test
    fun `should fetch products from network when cache is empty`() = runTest {
        // When - Call getLocalProducts with empty cache
        val result = productRepository.getLocalProducts()

        // Then - Should fetch from network and return products
        assertEquals(2, result.size)
        assertEquals("product-1", result[0].id)
        assertEquals("Test Product 1", result[0].name)
    }

    @Test
    fun `should handle network error when cache is empty and getLocalProducts is called`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """{"error": "Network error"}""",
            status = HttpStatusCode.ServiceUnavailable
        )

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getLocalProducts()
        }
    }

    // =================================
    // GET PRODUCT BY ID TESTS
    // =================================

    @Test
    fun `should successfully get product by id from cache`() = runTest {
        // Given - Populate cache first
        productRepository.getProducts()

        // When
        val result = productRepository.getProductById("product-1")

        // Then
        assertEquals("product-1", result.id)
        assertEquals("Test Product 1", result.name)
        assertEquals("Electronics", result.category)
    }

    @Test
    fun `should throw exception when product not found by id`() = runTest {
        // Given - Populate cache first
        productRepository.getProducts()

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getProductById("non-existent-product")
        }
    }

    @Test
    fun `should throw exception when getting product by id with empty cache`() = runTest {
        // When/Then - Cache is empty, should throw exception
        assertFailsWith<Exception> {
            productRepository.getProductById("product-1")
        }
    }

    @Test
    fun `should get correct product when multiple products exist`() = runTest {
        // Given - Populate cache first
        productRepository.getProducts()

        // When - Get second product
        val result = productRepository.getProductById("product-2")

        // Then
        assertEquals("product-2", result.id)
        assertEquals("Test Product 2", result.name)
        assertEquals("Books", result.category)
    }

    // =================================
    // DATA MAPPING TESTS
    // =================================

    @Test
    fun `should correctly map product response with all fields`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """[
                {
                    "id": "detailed-product",
                    "name": "Detailed Product",
                    "nameEn": "Detailed Product English",
                    "description": "This is a detailed product description",
                    "status": "ACTIVE",
                    "category": "Test Category",
                    "createdDate": "2024-03-15T14:30:00Z",
                    "units": [],
                    "prices": [],
                    "stocks": []
                }
            ]""",
            status = HttpStatusCode.OK
        )

        // When
        val result = productRepository.getProducts()

        // Then
        assertEquals(1, result.size)
        val product = result[0]
        assertEquals("detailed-product", product.id)
        assertEquals("Detailed Product", product.name)
        assertEquals("Detailed Product English", product.nameEn)
        assertEquals("This is a detailed product description", product.description)
        assertEquals("ACTIVE", product.status)
        assertEquals("Test Category", product.category)
        assertEquals("2024-03-15T14:30:00Z", product.createdDate)
        assertTrue(product.units.isEmpty())
        assertTrue(product.prices.isEmpty())
        assertTrue(product.stocks.isEmpty())
    }

    @Test
    fun `should handle product with null optional fields`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """[
                {
                    "id": "minimal-product",
                    "name": "Minimal Product",
                    "nameEn": null,
                    "description": null,
                    "status": "INACTIVE",
                    "category": "",
                    "createdDate": "2024-01-01T00:00:00Z",
                    "units": [],
                    "prices": [],
                    "stocks": []
                }
            ]""",
            status = HttpStatusCode.OK
        )

        // When
        val result = productRepository.getProducts()

        // Then
        assertEquals(1, result.size)
        val product = result[0]
        assertEquals("minimal-product", product.id)
        assertEquals("Minimal Product", product.name)
        assertEquals(null, product.nameEn)
        assertEquals(null, product.description)
        assertEquals("INACTIVE", product.status)
        assertEquals("", product.category)
    }

    // =================================
    // EDGE CASE TESTS
    // =================================

    @Test
    fun `should handle malformed JSON response`() = runTest {
        // Given
        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = """[{"malformed": json}]""",
            status = HttpStatusCode.OK
        )

        // When/Then
        assertFailsWith<Exception> {
            productRepository.getProducts()
        }
    }

    @Test
    fun `should handle large number of products`() = runTest {
        // Given
        val largeProductList = (1..100).joinToString(",") { index ->
            """{
                "id": "product-$index",
                "name": "Product $index",
                "nameEn": "Product $index EN",
                "description": "Description $index",
                "status": "ACTIVE",
                "category": "Category $index",
                "createdDate": "2024-01-01T00:00:00Z",
                "units": [],
                "prices": [],
                "stocks": []
            }"""
        }

        mockHttpModule.clearMockResponses()
        mockHttpModule.addMockResponse(
            url = "/api/pos/v1/products",
            content = "[$largeProductList]",
            status = HttpStatusCode.OK
        )

        // When
        val result = productRepository.getProducts()

        // Then
        assertEquals(100, result.size)
        assertEquals("product-1", result[0].id)
        assertEquals("product-100", result[99].id)

        // Test getProductById with large dataset
        val specificProduct = productRepository.getProductById("product-50")
        assertEquals("product-50", specificProduct.id)
        assertEquals("Product 50", specificProduct.name)
    }

    // =================================
    // INTEGRATION TESTS
    // =================================

    @Test
    fun `should handle complete product workflow`() = runTest {
        // When - Complete workflow: fetch -> cache -> get by id -> get local
        val networkProducts = productRepository.getProducts()
        val cachedProducts = productRepository.getLocalProducts()
        val specificProduct = productRepository.getProductById("product-1")

        // Then
        assertEquals(2, networkProducts.size)
        assertEquals(networkProducts.size, cachedProducts.size)
        assertEquals("product-1", specificProduct.id)
        assertEquals(networkProducts[0].id, specificProduct.id)
    }

    @Test
    fun `should maintain data consistency across multiple operations`() = runTest {
        // When - Multiple operations
        val products1 = productRepository.getProducts()
        val localProducts = productRepository.getLocalProducts()
        val productById = productRepository.getProductById("product-1")
        val products2 = productRepository.getProducts()

        // Then - All should return consistent data
        assertEquals(products1.size, localProducts.size)
        assertEquals(products1.size, products2.size)
        assertEquals(products1[0].id, productById.id)
        assertEquals(products1[0].name, productById.name)
    }

    @Test
    fun `should handle rapid successive calls`() = runTest {
        // Given
        val calls = 10
        val results = mutableListOf<List<Product>>()

        // When - Make rapid successive calls
        repeat(calls) {
            results.add(productRepository.getProducts())
        }

        // Then - All results should be consistent
        assertTrue(results.isNotEmpty())
        assertEquals(calls, results.size)
        results.forEach { productList ->
            assertEquals(2, productList.size)
            assertEquals("product-1", productList[0].id)
            assertEquals("product-2", productList[1].id)
        }
    }
}
