package com.testcode.kiosk.controller.product

import com.testcode.kiosk.ApiResponse
import com.testcode.kiosk.controller.product.dto.request.ProductCreateRequest
import com.testcode.kiosk.service.product.ProductService
import com.testcode.kiosk.service.product.response.ProductResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
@RestController
class ProductRestController(
    val productService: ProductService,
) {
    @PostMapping("/api/v1/products/new")
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ApiResponse<ProductResponse> {
        return ApiResponse.ok(productService.createProduct(request.toProductCreateServiceRequest()))
    }

    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): ApiResponse<List<ProductResponse>> {
        return ApiResponse.ok(productService.getSellingProducts())
    }
}
