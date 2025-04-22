package com.testcode.kiosk.service.order

import com.testcode.kiosk.domain.order.OrderRepository
import com.testcode.kiosk.domain.order.OrderStatus
import com.testcode.kiosk.service.mail.MailService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderStatisticsService(
    private val orderRepository: OrderRepository,
    private val mailService: MailService,
) {
    fun sendOrderStatisticsMail(orderDate: LocalDate, email: String): Boolean {
        val orders = orderRepository.findOrdersBy(
            startDateTime = orderDate.atStartOfDay(),
            endDateTime = orderDate.plusDays(1).atStartOfDay(),
            orderStatus = OrderStatus.PAYMENT_COMPLETED,
        )

        val totalAmount = orders.sumOf { it.totalPrice }

        val result = mailService.sendMail("no-reply@test.com", email, String.format("[매출 통계] %s", orderDate), String.format("총 매출 합계는 %s원 입니다.", totalAmount))

        if (!result) {
            throw IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.")
        }

        return true
    }
}
