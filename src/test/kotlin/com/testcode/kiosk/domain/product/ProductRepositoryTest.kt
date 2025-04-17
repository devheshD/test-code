package com.testcode.kiosk.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun `원하는 판매상태를 가진 상품들을 조회한다`() {
        // given
        val product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4000)
        val product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val products = productRepository.findAllBySellingStatusIn(listOf(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD))

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                tuple("002", "카페라떼", ProductSellingStatus.HOLD),
            )
    }

    @Test
    fun `상품번호 리스트로 상품들을 조회한다`() {
        // given
        val product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4000)
        val product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val products = productRepository.findAllByProductNumberIn(listOf("001", "002"))

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                tuple("002", "카페라떼", ProductSellingStatus.HOLD),
            )
    }

    @Test
    fun `가장 마지막으로 저장한 상품의 상품번호를 읽어온다`() {
        // given
        val targetProductNumber = "003"
        val product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4000)
        val product3 = createProduct(targetProductNumber, ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber)
    }

    @Test
    fun `가장 마지막으로 저장한 상품의 상품번호를 읽어올 때 상품이 하나도 없는 경우에는 null을 반환한다`() {
        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then
        assertThat(latestProductNumber).isNull()
    }

    private fun createProduct(productNumber: String, type: ProductType, sellingStatus: ProductSellingStatus, name: String, price: Int): Product {
        return Product(
            productNumber = productNumber,
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price,
        )
    }
}
