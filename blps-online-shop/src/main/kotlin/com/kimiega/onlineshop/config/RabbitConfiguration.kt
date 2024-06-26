package com.kimiega.onlineshop.config

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfiguration {
    @Bean
    fun connectionFactory(): ConnectionFactory {
        return CachingConnectionFactory("rabbitmq")
    }

    @Bean
    fun amqpAdmin(): AmqpAdmin {
        return RabbitAdmin(connectionFactory())
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(connectionFactory())
    }

    @Bean
    fun notificationQueue(): Queue {
        return Queue("notificationQueue")
    }

    @Bean
    fun newsletterQueue(): Queue {
        return Queue("newsletterQueue")
    }
}
