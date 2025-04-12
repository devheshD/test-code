package com.testcode.kiosk.controller.order

import com.testcode.kiosk.service.order.OrderService
import com.testcode.kiosk.service.order.request.OrderCreateRequest
import com.testcode.kiosk.service.order.response.OrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderRestController(
    private val orderService: OrderService,
) {
    @PostMapping("/api/v1/orders/new")
    fun createOrder(@RequestBody request: OrderCreateRequest): OrderResponse {
        val registeredDateTime = LocalDateTime.now()
        return orderService.createOrder(request, registeredDateTime)
    }
}
