package com.testcode.kiosk.service.product

import com.testcode.kiosk.controller.product.dto.request.ProductCreateRequest
import com.testcode.kiosk.domain.product.ProductRepository
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.service.product.request.ProductCreateServiceRequest
import com.testcode.kiosk.service.product.response.ProductResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProductService(
    val productRepository: ProductRepository,
) {
    @Transactional
    fun createProduct(request: ProductCreateServiceRequest): ProductResponse {
        val nextProductNumber = createNextProductNumber()

        val product = request.toEntity(nextProductNumber)
        val savedProduct = productRepository.save(product)

        return ProductResponse.of(savedProduct)
    }

    fun getSellingProducts(): List<ProductResponse> {
        val products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())

        return products.map {
            ProductResponse.of(it)
        }
    }

    private fun createNextProductNumber(): String {
        val latestProductNumber = productRepository.findLatestProductNumber()
        if (latestProductNumber == null) {
            return "001"
        }

        val latestProductNumberInt = latestProductNumber.toInt()
        val nextProductNumberInt = latestProductNumberInt + 1

        return String.format("%03d", nextProductNumberInt)
    }
}
