package com.kimiega.notificationconsumer.service

import com.kimiega.onlineshop.common.entity.Notification

interface NotificationService {
    fun send(notification: Notification)
}
