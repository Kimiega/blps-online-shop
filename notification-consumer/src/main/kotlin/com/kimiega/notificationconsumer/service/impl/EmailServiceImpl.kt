package com.kimiega.notificationconsumer.service.impl

import com.kimiega.notificationconsumer.service.EmailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl(
    private val emailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val fromEmail: String
): EmailService {
    override fun sendSimpleMessage(
        to: String,
        subject: String,
        text: String,
        ) {
        val message = SimpleMailMessage().apply {
            from = fromEmail
            setTo(to)
            setSubject(subject)
            setText(text)
        }
        emailSender.send(message)
    }
}
