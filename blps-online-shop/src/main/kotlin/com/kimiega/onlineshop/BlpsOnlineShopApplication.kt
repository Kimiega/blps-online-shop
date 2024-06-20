package com.kimiega.onlineshop

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
class BlpsOnlineShopApplication

fun main(args: Array<String>) {
    runApplication<BlpsOnlineShopApplication>(*args)
}
