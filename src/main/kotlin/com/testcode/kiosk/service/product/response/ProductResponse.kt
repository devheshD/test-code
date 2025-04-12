package com.testcode.kiosk.service.product.response

import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType

data class ProductResponse(
    val id: Long,
    val productNumber: String,
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    companion object {
        fun of(product: Product) = ProductResponse(
            id = product.id,
            productNumber = product.productNumber,
            type = product.type,
            sellingStatus = product.sellingStatus,
            name = product.name,
            price = product.price
        )
    }
}
