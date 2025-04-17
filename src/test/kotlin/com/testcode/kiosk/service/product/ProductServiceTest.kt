package com.testcode.kiosk.service.product

import com.testcode.kiosk.controller.product.dto.request.ProductCreateRequest
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var productRepository: ProductRepository

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
    }

    @Test
    fun `신규 상품을 등록(상품 번호는 가장 최근 상품의 상품번호에서 1증가한 값)`() {
        // given
        val product = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        productRepository.save(product)

        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "카푸치노",
            price = 5000,
        )

        // when
        val productResponse = productService.createProduct(request)

        // then
        assertThat(productResponse).extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)

        val products = productRepository.findAll()
        assertThat(products).hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000),
                tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000),
            )
    }

    @Test
    fun `상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다`() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "카푸치노",
            price = 5000,
        )

        // when
        val productResponse = productService.createProduct(request)

        // then
        assertThat(productResponse).extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)

        val products = productRepository.findAll()
        assertThat(products).hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains(
                tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000),
            )
    }

    private fun createProduct(productNumber: String, type: ProductType, sellingStatus: ProductSellingStatus, name: String, price: Int): Product {
        return Product(
            productNumber = productNumber,
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price,
        )
    }
}
