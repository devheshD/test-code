package com.testcode.kiosk.service.order.request

data class OrderCreateRequest(
    val productNumbers: List<String>
) {
    fun toServiceRequest(): OrderCreateServiceRequest {
        return OrderCreateServiceRequest(
            productNumbers = productNumbers
        )
    }
}
