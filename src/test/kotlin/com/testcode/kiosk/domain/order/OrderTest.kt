package com.testcode.kiosk.domain.order

import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDateTime
import kotlin.test.Test

class OrderTest {

    @Test
    fun `주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다`() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = Order.create(products, LocalDateTime.now())

        // then
        assertThat(order.totalPrice).isEqualTo(3000)
    }

    @Test
    fun `주문 생성시 주문 상태는 INIT이다`() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = Order.create(products, LocalDateTime.now())

        // then
        assertThat(order.orderStatus).isEqualByComparingTo(OrderStatus.INIT)
    }

    @Test
    fun `주문 생성시 주문 등록 시간을 기록한다`() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = Order.create(products, registeredDateTime)

        // then
        assertThat(order.orderStatus).isEqualByComparingTo(OrderStatus.INIT)
    }

    private fun createProduct(productNumber: String, price: Int): Product = Product(
        productNumber = productNumber,
        type = ProductType.HANDMADE,
        sellingStatus = ProductSellingStatus.SELLING,
        name = "메뉴 이름",
        price = price
    )
}
