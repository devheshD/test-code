package com.testcode.kiosk.domain.order

enum class OrderStatus(
    private val description: String,
) {
    INIT("주문생성"),
    CANCELED("주문취소"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_FAILED("결제실패"),
    RECEIVED("주문접수"),
    COMPLETED("주문완료"),
    ;
}
