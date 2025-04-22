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
        // 해당 일자에 결제 완료된 주문들을 가져와서
        val orders = orderRepository.findOrdersBy(
            startDateTime = orderDate.atStartOfDay(),
            endDateTime = orderDate.plusDays(1).atStartOfDay(),
            orderStatus = OrderStatus.PAYMENT_COMPLETED,
        )

        // 총 매출 합계를 계산
        val totalAmount = orders.sumOf { it.totalPrice }

        // 메일 전송
        val result = mailService.sendMail("no-reply@test.com", email, String.format("[매출 통계] %s", orderDate), String.format("총 매출 합계는 %s원 입니다.", totalAmount))

        if (!result) {
            throw IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.")
        }

        return true
    }
}
