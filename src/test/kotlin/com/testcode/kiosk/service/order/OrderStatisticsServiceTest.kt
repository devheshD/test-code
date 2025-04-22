package com.testcode.kiosk.service.order

import com.testcode.kiosk.client.mail.MailSendClient
import com.testcode.kiosk.domain.history.mail.MailSendHistoryRepository
import com.testcode.kiosk.domain.order.Order
import com.testcode.kiosk.domain.order.OrderRepository
import com.testcode.kiosk.domain.order.OrderStatus
import com.testcode.kiosk.domain.orderproduct.OrderProductRepository
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private lateinit var orderStatisticsService: OrderStatisticsService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderProductRepository: OrderProductRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var mailSendHistoryRepository: MailSendHistoryRepository

    @MockitoBean
    lateinit var mailSendClient: MailSendClient

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAll()
        orderRepository.deleteAll()
        productRepository.deleteAll()
        mailSendHistoryRepository.deleteAll()
    }

    @Test
    fun `결제 완료 주문들을 조회하여 매출 통계 메일을 전송한다`() {
        // given
        val now = LocalDateTime.of(2023, 3, 5, 0, 0)

        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 2000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 3000)
        val products = listOf(product1, product2, product3)
        productRepository.saveAll(products)
        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 4, 23, 59, 59), products)
        createPaymentCompletedOrder(now, products)
        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 5, 23, 59, 59), products)
        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 6, 0, 0), products)

        // stubbing
        Mockito.`when`(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(true)

        // when
        val result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com")

        // then
        assertThat(result).isTrue

        val histories = mailSendHistoryRepository.findAll()
        assertThat(histories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 18000원 입니다.")
    }

    private fun createPaymentCompletedOrder(
        now: LocalDateTime,
        products: List<Product>
    ) {
        val order = Order(
            products = products,
            registeredDateTime = now
        ).apply {
            this.orderStatus = OrderStatus.PAYMENT_COMPLETED
        }
        orderRepository.save(order)
    }

    private fun createProduct(type: ProductType, productNumber: String, price: Int): Product = Product(
        productNumber = productNumber,
        type = type,
        sellingStatus = ProductSellingStatus.SELLING,
        name = "메뉴 이름",
        price = price
    )
}
