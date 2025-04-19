package com.testcode.kiosk.controller.product.dto.request

import com.testcode.kiosk.domain.product.Product
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductCreateRequest(
    @NotNull(message = "상품 타입은 필수입니다.")
    val type: ProductType,

    @NotNull(message = "상품 판매상태는 필수입니다.")
    val sellingStatus: ProductSellingStatus,

    @NotBlank(message = "상품 이름은 필수입니다.")
    @Max(20)
    val name: String,

    @Positive(message = "상품 가격은 양수여야 합니다.")
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
