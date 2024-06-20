package com.kimiega.notificationconsumer.service

interface EmailService {
    fun sendSimpleMessage(
        to: String,
        subject: String,
        text: String,
    )
}