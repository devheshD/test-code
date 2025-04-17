package com.testcode.kiosk.controller.product

import com.testcode.kiosk.controller.product.dto.request.ProductCreateRequest
import com.testcode.kiosk.service.product.ProductService
import com.testcode.kiosk.service.product.response.ProductResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
@RestController
class ProductRestController(
    val productService: ProductService,
) {
    @PostMapping("/api/v1/products/new")
    fun createProduct(request: ProductCreateRequest) {
        productService.createProduct(request)
    }

    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): List<ProductResponse> {
        return productService.getSellingProducts()
    }
}
