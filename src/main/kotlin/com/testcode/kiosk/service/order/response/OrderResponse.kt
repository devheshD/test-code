package com.testcode.kiosk.service.order.response

import com.testcode.kiosk.domain.order.Order
import com.testcode.kiosk.service.product.response.ProductResponse
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val totalPrice: Int,
    val registeredDateTime: LocalDateTime,
    val products: List<ProductResponse>,
) {
    companion object {
        fun of(order: Order): OrderResponse {
            return OrderResponse(
                id = order.id,
                totalPrice = order.totalPrice,
                registeredDateTime = order.registeredDateTime,
                products = order.orderProducts.map { ProductResponse.of(it.product) }
            )
        }
    }
}
