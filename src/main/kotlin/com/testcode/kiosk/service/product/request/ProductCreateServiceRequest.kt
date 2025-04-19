package com.testcode.kiosk.service.product.request

import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType

data class ProductCreateServiceRequest(
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
