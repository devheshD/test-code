package com.testcode.kiosk.controller.product.dto.request

import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType

data class ProductCreateRequest(
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    fun toEntity(productNumber: String) = Product(
        productNumber = productNumber,
        type = type,
        sellingStatus = sellingStatus,
        name = name,
        price = price,
    )
}
