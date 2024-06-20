package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.common.entity.Notification
import com.kimiega.onlineshop.service.NotificationService
import com.kimiega.onlineshop.common.utils.convertObjectToJson
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val amqpTemplate: AmqpTemplate,
): NotificationService {
    override fun sendNotification(notification: Notification) {
        amqpTemplate.convertAndSend("notificationQueue", convertObjectToJson(notification))
    }
}