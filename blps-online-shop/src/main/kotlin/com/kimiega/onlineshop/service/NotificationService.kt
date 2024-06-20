package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.common.entity.Notification


interface NotificationService {

    fun sendNotification(notification: Notification)
}