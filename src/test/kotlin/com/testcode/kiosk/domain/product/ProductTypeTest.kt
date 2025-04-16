package com.testcode.kiosk.domain.product

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class ProductTypeTest {

    @Test
    fun `상품 타입이 재고 관련 타입인지를 체크한다`() {
        // given
        val givenType = ProductType.HANDMADE

        // when
        val result = ProductType.containsStockType(givenType)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `상품 타입이 재고 관련 타입인지를 체크한다2`() {
        // given
        val givenType = ProductType.BAKERY

        // when
        val result = ProductType.containsStockType(givenType)

        // then
        assertThat(result).isTrue
    }
}
