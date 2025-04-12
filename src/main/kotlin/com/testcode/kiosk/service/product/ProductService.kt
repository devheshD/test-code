package com.testcode.kiosk.service.product

import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.service.product.response.ProductResponse
import org.springframework.stereotype.Service

@Service
class ProductService(
    val productRepository: ProductRepository,
) {
    fun getSellingProducts(): List<ProductResponse> {
        val products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())

        return products.map {
            ProductResponse.of(it)
        }
    }
}
