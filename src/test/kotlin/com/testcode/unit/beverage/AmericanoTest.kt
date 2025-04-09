package com.testcode.unit.beverage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AmericanoTest {

    @Test
    fun `아메리카노 이름은 아메리카노`() {
        val americano = Americano()

//        assertEquals(americano.getName(), "아메리카노")
        assertThat(americano.getName()).isEqualTo("아메리카노")
    }
}
