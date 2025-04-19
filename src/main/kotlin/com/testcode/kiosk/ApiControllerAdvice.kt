package com.testcode.kiosk

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    fun bindException(e: BindException): ApiResponse<Object> {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.bindingResult.allErrors[0].defaultMessage as Object,
        )
    }
}
