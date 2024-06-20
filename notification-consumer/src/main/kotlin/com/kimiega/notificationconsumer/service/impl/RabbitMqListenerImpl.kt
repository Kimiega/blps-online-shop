package com.kimiega.notificationconsumer.service.impl

import com.kimiega.onlineshop.common.entity.Notification
import com.kimiega.notificationconsumer.service.NotificationService
import com.kimiega.onlineshop.common.utils.convertJsonToObject
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@EnableRabbit
@Component
class RabbitMqListenerImpl(
    private val notificationService: NotificationService,
) {

    @RabbitListener(queues = ["notificationQueue", "newsletterQueue"])
    fun listenNotificationQueue(notification: String) {
        notificationService.send(convertJsonToObject(notification, Notification::class.java))
    }
}