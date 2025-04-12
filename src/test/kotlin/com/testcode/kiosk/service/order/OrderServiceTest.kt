package com.testcode.kiosk.service.order

import com.testcode.kiosk.domain.orderproduct.OrderProductRepository
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import com.testcode.kiosk.service.order.request.OrderCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes.tuple
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import kotlin.test.Test

@ActiveProfiles("test")
@SpringBootTest
//@DataJpaTest
class OrderServiceTest {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var orderRepository: ProductRepository

    @Autowired
    lateinit var orderProductRepository: OrderProductRepository

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
    }

    @Test
    fun `주문번호 리스트를 받아 주문을 생성한다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(
            productNumbers = listOf("001", "002")
        )

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 4000)
        assertThat(orderResponse.products).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000)
            )
    }

    @Test
    fun `중복되는 상품 번호 리스트로 주문을 생성할 수 있다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(
            productNumbers = listOf("001", "001")
        )

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 2000)
        assertThat(orderResponse.products).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000)
            )
    }

    private fun createProduct(type: ProductType, productNumber: String, price: Int): Product = Product(
        productNumber = productNumber,
        type = type,
        sellingStatus = ProductSellingStatus.SELLING,
        name = "메뉴 이름",
        price = price
    )
}
