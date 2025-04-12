package com.testcode.kiosk.domain.product

import com.testcode.kiosk.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val productNumber: String,
    @Enumerated(EnumType.STRING)
    val type: ProductType,
    @Enumerated(EnumType.STRING)
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
): BaseEntity()
