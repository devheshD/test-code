package com.testcode.kiosk.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.testcode.kiosk.controller.product.ProductRestController
import com.testcode.kiosk.controller.product.dto.request.ProductCreateRequest
import com.testcode.kiosk.domain.product.ProductSellingStatus
import com.testcode.kiosk.domain.product.ProductType
import com.testcode.kiosk.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.*
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(controllers = [ProductRestController::class])
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var productService: ProductService

    @Test
    fun `신규 상품을 등록한다`() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "아메리카노",
            price = 400
        )

        // when // then
        mockMvc.perform(
            post("/api/v1/products/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(APPLICATION_JSON)
        ).andExpect(
            status().isOk
        )
    }

    @Test
    fun `판매 상품을 조회한다`() {
        // given
        // when
        // then
    }
}
