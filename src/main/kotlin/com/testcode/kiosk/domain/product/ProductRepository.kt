package com.testcode.kiosk.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where selling_status in ('SELLING', 'HOLD');
     */
    fun findAllBySellingStatusIn(sellingTypes: List<ProductSellingStatus>): List<Product>
    fun findAllByProductNumberIn(productNumbers: List<String>): List<Product>
}
