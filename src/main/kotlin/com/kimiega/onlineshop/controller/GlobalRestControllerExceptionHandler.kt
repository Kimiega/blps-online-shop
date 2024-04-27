package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.exception.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [
            NoOrderStatusException::class, NoPaymentExistsException::class, NoSuchOrderException::class, NoSuchProductException::class
        ])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleNotFoundException(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = ex.message!!
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }

    @ExceptionHandler(value = [
       NotEnoughProductsException::class
    ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleNotEnoughProducts(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = ex.message!!
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(value = [
        OrderNotPaidException::class
    ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleOrderNotPaid(
        ex: RuntimeException, request: WebRequest,
    ): ResponseEntity<Any>? {
        val bodyOfResponse = ex.message!!
        return handleExceptionInternal(
            ex, bodyOfResponse,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }
}