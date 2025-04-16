package com.testcode.kiosk.domain.stock

import com.testcode.kiosk.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlin.compareTo

@Entity
data class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val productNumber: String,
    var quantity: Int,
): BaseEntity() {

    fun isQuantityLessThan(quantity: Int): Boolean {
        return this.quantity < quantity
    }

    fun deductQuantity(quantity: Int) {
        if (isQuantityLessThan(quantity)) {
            throw IllegalArgumentException("차감할 재고 수량이 없습니다.")
        }
        this.quantity -= quantity
    }

    companion object {
        fun create(productNumber: String, quantity: Int): Stock {
            return Stock(
                productNumber = productNumber,
                quantity = quantity
            )
        }
    }
}
