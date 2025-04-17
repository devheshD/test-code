package com.testcode.kiosk.service.order

import com.testcode.kiosk.domain.order.Order
import com.testcode.kiosk.domain.order.OrderRepository
import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductType
import com.testcode.kiosk.domain.stock.StockRepository
import com.testcode.kiosk.service.order.request.OrderCreateRequest
import com.testcode.kiosk.service.order.response.OrderResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val stockRepository: StockRepository,
) {
    /**
     * 재고 감소 -> 동시성 고민..
     * optimistic lock, pessimistic lock
     */
    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val products = findProductsBy(productNumbers)

        deductStockQuantities(products)

        val order = Order.create(products, registeredDateTime)
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

    private fun deductStockQuantities(products: List<Product>) {
        val stockProductNumbers = products.filter { ProductType.containsStockType(it.type) }
            .map { it.productNumber }

        val stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers)
        val stockMap = stocks.associateBy { it.productNumber }

        val productCountingMap = stockProductNumbers.groupingBy { it }.eachCount()

        for (stockProductNumber in stockProductNumbers.distinct()) {
            val stock = stockMap[stockProductNumber]!!
            val quantity = productCountingMap[stockProductNumber]!!
            if (stock.isQuantityLessThan(quantity)) {
                throw IllegalArgumentException("재고가 부족한 상품이 있습니다.")
            }
            stock.deductQuantity(quantity)
        }
    }
}
