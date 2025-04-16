package com.testcode.kiosk.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class StockTest {

    @Test
    fun `재고의 수량이 제공된 수량보다 작은지 확인한다`() {
        // given
        val stock = Stock.create("001", 1)
        val quantity = 2

        // when
        val result = stock.isQuantityLessThan(quantity)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `재고를 주어진 개수만큼 차감할 수 있다`() {
        // given
        val stock = Stock.create("001", 1)
        val quantity = 1

        // when
        stock.deductQuantity(quantity)

        // then
        assertThat(stock.quantity).isZero
    }

    @Test
    fun `재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다`() {
        // given
        val stock = Stock.create("001", 1)
        val quantity = 2

        // when
        // then
        val exception = assertThrows<IllegalArgumentException> {
            stock.deductQuantity(quantity)
        }

        assertThat(exception.message).isEqualTo("차감할 재고 수량이 없습니다.")
    }
}
