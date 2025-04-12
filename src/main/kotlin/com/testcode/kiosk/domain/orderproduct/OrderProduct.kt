package com.testcode.kiosk.domain.orderproduct

import com.testcode.kiosk.domain.order.Order
import com.testcode.kiosk.domain.product.Product
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class OrderProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
)
