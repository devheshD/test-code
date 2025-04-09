package com.testcode.unit.order

import com.testcode.unit.beverage.Beverage
import java.time.LocalDateTime

data class Order(
    val orderTime: LocalDateTime = LocalDateTime.now(),
    val beverage: List<Beverage> = emptyList()
)
