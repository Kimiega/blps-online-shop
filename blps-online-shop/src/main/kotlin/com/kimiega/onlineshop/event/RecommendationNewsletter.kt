package com.kimiega.onlineshop.event

import com.kimiega.onlineshop.common.entity.Notification
import com.kimiega.onlineshop.entity.Product
import com.kimiega.onlineshop.entity.Roles
import com.kimiega.onlineshop.security.service.UserService
import com.kimiega.onlineshop.service.ProductService
import com.kimiega.onlineshop.common.utils.convertObjectToJson
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class RecommendationNewsletter(
    private val productService: ProductService,
    private val userService: UserService,
    private val amqpTemplate: AmqpTemplate,
) {
    @Scheduled(cron = "\${recommendation.product-of-day.cron}")
    fun sendNewsletter() {
        val emails = getUserEmails()
        val productsOfDay = getProductsOfDay()
        if (productsOfDay.isEmpty())
            return
        emails.parallelStream().forEach {
            amqpTemplate.convertAndSend(
                "newsletterQueue",
                convertObjectToJson(createNotification(
                    email = it,
                    subject = "Products of day",
                    text = productsOfDay.toString())))
        }

    }

    private fun getUserEmails(): List<String> {
        return userService.findUsers().stream().filter { user ->
            user.roles.any { it.name == Roles.CUSTOMER.name }
        }.map { it.email }.toList()
    }

    private fun getProductsOfDay(): List<Product> {
        val actualProducts = productService.getProducts()
        return actualProducts.shuffled().subList(0, min(3, actualProducts.size))

    }
    private fun createNotification(email: String, subject: String, text: String) =
        Notification(email, subject, text)
}