package com.testcode.kiosk.domain.stock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository: JpaRepository<Stock, Long> {
    fun findAllByProductNumberIn(productNumber: List<String>): List<Stock>
}
