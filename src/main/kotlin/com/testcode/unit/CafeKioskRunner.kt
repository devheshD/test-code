package com.testcode.unit

import com.testcode.unit.beverage.Americano
import com.testcode.unit.beverage.Latte

class CafeKioskRunner

fun main() {
    val cafeKiosk = CafeKiosk()
    cafeKiosk.add(Americano())
    println(">> 아메리카노 추가")

    cafeKiosk.add(Latte())
    println(">> 라떼 추가")
    
    val totalPrice = cafeKiosk.calculateTotalPrice()
    println("총 주문 가격 : $totalPrice")
}
