package com.testcode.unit

import com.testcode.unit.beverage.Americano
import com.testcode.unit.beverage.Latte
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CafeKioskTest {

    @Test
    fun `음료 값을 더한값 검증`() {
        // given
        val cafeKiosk = CafeKiosk()

        // when
        cafeKiosk.add(Americano())
        cafeKiosk.add(Latte())

        // then
        println(">> 담긴 음료 수 : ${cafeKiosk.beverages.size}")
        println(">> 담긴 음료 : ${cafeKiosk.beverages[0].getName()}")
        assertEquals(8500, cafeKiosk.calculateTotalPrice())
    }

    @Test
    fun `음료 추가`() {
        // given
        val cafeKiosk = CafeKiosk()

        // when
        cafeKiosk.add(Americano())

        // then
        assertThat(cafeKiosk.beverages.size).isEqualTo(1)
        assertThat(cafeKiosk.beverages[0].getName()).isEqualTo("아메리카노")
    }

    @Test
    fun `음료 카운팅으로 추가`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        // when
        cafeKiosk.add(americano, 2)

        // then
        assertThat(cafeKiosk.beverages[0]).isEqualTo(americano)
        assertThat(cafeKiosk.beverages[1]).isEqualTo(americano)
    }

    @Test
    fun `음료를 0개 추가시 예외`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        // when
        val exception = assertThrows<IllegalArgumentException> {
            cafeKiosk.add(americano, 0)
        }

        // then
        assertThat(exception.message).isEqualTo("음료는 1잔 이상 주문하실 수 있습니다.")
    }

    @Test
    fun `음료 단건 제거`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        // when
        cafeKiosk.add(americano)

        // then
        assertThat(cafeKiosk.beverages.size).isEqualTo(1)

        // when
        cafeKiosk.remove(americano)

        // then
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    @Test
    fun `음료 전체 삭제`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        // when
        cafeKiosk.add(americano)
        cafeKiosk.add(latte)

        // then
        assertThat(cafeKiosk.beverages).hasSize(2)

        // when
        cafeKiosk.clear()

        // then
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    @Test
    fun `주문 생성`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        // when
        cafeKiosk.add(americano)
        val order = cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 1, 11, 0, 0))

        // then
        assertThat(order.beverage).hasSize(1)
        assertThat(order.beverage[0].getName()).isEqualTo("아메리카노")
    }

    @Test
    fun `주문 생성시간 이외인 경우 예외`() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        // when
        cafeKiosk.add(americano)
        val exception = assertThrows<IllegalArgumentException> {
            cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 1, 23, 0, 0))
        }

        // then
        assertThat(exception.message).isEqualTo("주문 시간이 아닙니다. 관리자에게 문의하세요.")
    }
}
