package com.testcode.unit

import com.testcode.unit.beverage.Beverage
import com.testcode.unit.order.Order
import java.time.LocalDateTime

class CafeKiosk {

    val beverages = mutableListOf<Beverage>()

    fun add(beverage: Beverage) {
        beverages.add(beverage)
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

    fun createOrder(): Order {
        return Order(LocalDateTime.now(), beverages)
    }
}
