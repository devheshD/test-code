package com.testcode.unit

import com.testcode.unit.beverage.Beverage
import com.testcode.unit.order.Order
import java.time.LocalDateTime
import java.time.LocalTime

class CafeKiosk {
    val beverages = mutableListOf<Beverage>()

    fun add(beverage: Beverage) {
        beverages.add(beverage)
    }

    fun add(beverage: Beverage, count: Int) {
        if (count <= 0) {
            throw IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.")
        }

        repeat(count) {
            beverages.add(beverage)
        }
    }

    fun remove(beverage: Beverage) {
        beverages.remove(beverage)
    }

    fun clear() {
        beverages.clear()
    }

    fun calculateTotalPrice(): Int {
        return beverages.sumOf { it.getPrice() }
    }

    fun createOrder(currentDateTime: LocalDateTime): Order {
        val currentTime = currentDateTime.toLocalTime()
        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.")
        }

        return Order(currentDateTime, beverages)
    }

    companion object {
        val SHOP_OPEN_TIME = LocalTime.of(10, 0)!!
        val SHOP_CLOSE_TIME = LocalTime.of(11, 0)!!
    }
}
