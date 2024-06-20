package com.kimiega.notificationconsumer.service.impl

import com.kimiega.onlineshop.common.entity.Notification
import com.kimiega.notificationconsumer.service.EmailService
import com.kimiega.notificationconsumer.service.NotificationService
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val emailService: EmailService,
) : NotificationService {
    override fun send(notification: Notification) {
        emailService.sendSimpleMessage(
            notification.email,
            notification.subject,
            notification.message,
        )
    }

}