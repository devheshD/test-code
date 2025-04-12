package com.testcode.kiosk.service.order

import com.testcode.kiosk.domain.order.Order
import com.testcode.kiosk.domain.order.OrderRepository
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.service.order.request.OrderCreateRequest
import com.testcode.kiosk.service.order.response.OrderResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
) {
    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val duplicateProducts = findProductsBy(productNumbers)

        val order = Order.create(duplicateProducts, registeredDateTime)

        val savedOrder = orderRepository.save(order)

        return OrderResponse.of(savedOrder)
    }

    private fun findProductsBy(productNumbers: List<String>): List<Product> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val productMap = products.associateBy { it.productNumber }

        return productNumbers.mapNotNull { productNumber ->
            productMap[productNumber]
        }
    }
}
