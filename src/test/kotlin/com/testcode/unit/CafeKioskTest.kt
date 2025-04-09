package com.testcode.unit

import com.testcode.unit.beverage.Americano
import com.testcode.unit.beverage.Latte
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CafeKioskTest {

    // 다른 사람이 이 코드 봤을때 무엇을 검증하는건지 모름 무조건 성공하는 케이스이기 때문에
    @Test
    fun `계산 더하기`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())
        cafeKiosk.add(Latte())

        println(">> 담긴 음료 수 : ${cafeKiosk.beverages.size}")
        println(">> 담긴 음료 : ${cafeKiosk.beverages[0].getName()}")

        assertEquals(8500, cafeKiosk.calculateTotalPrice())
    }

    @Test
    fun `정상 동작 테스트`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        assertThat(cafeKiosk.beverages.size).isEqualTo(1)
        assertThat(cafeKiosk.beverages[0].getName()).isEqualTo("아메리카노")
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
}
