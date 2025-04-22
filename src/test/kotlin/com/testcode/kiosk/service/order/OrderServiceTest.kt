package com.testcode.kiosk.service.order

import com.testcode.kiosk.domain.orderproduct.OrderProductRepository
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import com.testcode.kiosk.domain.stock.Stock
import com.testcode.kiosk.domain.stock.StockRepository
import com.testcode.kiosk.service.order.request.OrderCreateServiceRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import kotlin.test.Test

@ActiveProfiles("test")
//@Transactional
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
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var orderProductRepository: OrderProductRepository

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
        stockRepository.deleteAllInBatch()
    }

    @Test
    fun `주문번호 리스트를 받아 주문을 생성한다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateServiceRequest(
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

        val request = OrderCreateServiceRequest(
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

    @Test
    fun `재고와 관련된 상품이 포함되어 있는 주문 번호 리스트를 받아 주문을 생성한다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val product1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val product2 = createProduct(ProductType.BAKERY, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock.create("001", 2)
        val stock2 = Stock.create("002", 2)
        stockRepository.saveAll(listOf(stock1, stock2))

        val request = OrderCreateServiceRequest(
            productNumbers = listOf("001", "001", "002", "003")
        )

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 10000)
        assertThat(orderResponse.products).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
                tuple("002", 3000),
                tuple("003", 5000),
            )

        val stocks = stockRepository.findAll()
        assertThat(stocks).hasSize(2)
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1),
            )
    }

    @Test
    fun `재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다 생성한다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val product1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val product2 = createProduct(ProductType.BAKERY, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock.create("001", 2)
        val stock2 = Stock.create("002", 2)
        stock1.deductQuantity(1) // todo
        stockRepository.saveAll(listOf(stock1, stock2))

        val request = OrderCreateServiceRequest(
            productNumbers = listOf("001", "001", "002", "003")
        )

        // when
        val exception = assertThrows<IllegalArgumentException> {
            orderService.createOrder(request, registeredDateTime)
        }

        // then
        assertThat(exception.message).isEqualTo("재고가 부족한 상품이 있습니다.")
    }

    private fun createProduct(type: ProductType, productNumber: String, price: Int): Product = Product(
        productNumber = productNumber,
        type = type,
        sellingStatus = ProductSellingStatus.SELLING,
        name = "메뉴 이름",
        price = price
    )
}
