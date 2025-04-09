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
    fun `계산 더하기 테스트`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())
        cafeKiosk.add(Latte())

        println(">> 담긴 음료 수 : ${cafeKiosk.beverages.size}")
        println(">> 담긴 음료 : ${cafeKiosk.beverages[0].getName()}")

        assertEquals(8500, cafeKiosk.calculateTotalPrice())
    }

    @Test
    fun `음료 추가 테스트`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        assertThat(cafeKiosk.beverages.size).isEqualTo(1)
        assertThat(cafeKiosk.beverages[0].getName()).isEqualTo("아메리카노")
    }

    @Test
    fun `음료 카운팅으로 추가`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano, 2)

        assertThat(cafeKiosk.beverages[0]).isEqualTo(americano)
        assertThat(cafeKiosk.beverages[1]).isEqualTo(americano)
    }

    @Test
    fun `음료를 0개 추가시 예외`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        val exception = assertThrows<IllegalArgumentException> {
            cafeKiosk.add(americano, 0)
        }

        assertThat(exception.message).isEqualTo("음료는 1잔 이상 주문하실 수 있습니다.")
    }

    @Test
    fun `음료 제거 테스트`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano)
        assertThat(cafeKiosk.beverages.size).isEqualTo(1)

        cafeKiosk.remove(americano)
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    @Test
    fun `음료 클리어 테스트`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.add(americano)
        cafeKiosk.add(latte)
        assertThat(cafeKiosk.beverages).hasSize(2)

        cafeKiosk.clear()
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    // 항상 성공하는 테스트가 아님
    // 주문 시간 범위밖이면 예외가 발생함
    @Test
    fun `주문 생성`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano)
        val order = cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 1, 11, 0, 0))

        assertThat(order.beverage).hasSize(1)
        assertThat(order.beverage[0].getName()).isEqualTo("아메리카노")
    }

    @Test
    fun `주문 생성시간 이외인 경우 예외`() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano)

        val exception = assertThrows<IllegalArgumentException> {
            cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 1, 23, 0, 0))
        }

        assertThat(exception.message).isEqualTo("주문 시간이 아닙니다. 관리자에게 문의하세요.")
    }

}
