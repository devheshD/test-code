package com.testcode.kiosk

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val code: Int = 0,
    val status: HttpStatus,
    val message: String,
    val date: T,
) {
    companion object {
        fun <T> of(
            status: HttpStatus,
            message: String,
            date: T,
        ): ApiResponse<T> {
            return ApiResponse(status.value(), status, message, date)
        }

        fun <T> of(
            status: HttpStatus,
            date: T,
        ): ApiResponse<T> {
            return of(status, status.name, date)
        }

        fun <T> ok(
            date: T,
        ): ApiResponse<T> {
            return of(HttpStatus.OK, date)
        }
    }
}
