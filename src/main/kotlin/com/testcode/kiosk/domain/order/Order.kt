package com.testcode.kiosk.domain.order

import com.testcode.kiosk.domain.BaseEntity
import com.testcode.kiosk.domain.orderproduct.OrderProduct
import com.testcode.kiosk.domain.product.Product
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    products: List<Product>,
    val registeredDateTime: LocalDateTime
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.INIT

    var totalPrice: Int = calculateTotalPrice(products)

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderProducts: MutableList<OrderProduct> = mutableListOf()

    companion object {
        fun create(products: List<Product>, registeredDateTime: LocalDateTime): Order {
            return Order(
                products = products,
                registeredDateTime = registeredDateTime,
            ).apply {
                this.orderStatus = OrderStatus.INIT
                this.totalPrice = calculateTotalPrice(products)
                this.orderProducts = products.map { product ->
                    OrderProduct(id, this, product)
                }.toMutableList()
            }
        }
    }

    private fun calculateTotalPrice(products: List<Product>) = products.sumOf { it.price }
}
