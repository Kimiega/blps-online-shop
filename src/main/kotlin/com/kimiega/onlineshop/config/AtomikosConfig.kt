package com.kimiega.onlineshop.config

import com.atomikos.icatch.jta.UserTransactionManager
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.jta.JtaTransactionManager


@Configuration
@EnableConfigurationProperties(value = [Database1::class, Database2::class])
@EnableTransactionManagement
class AtomikosConfig {
    @Bean(initMethod = "init", destroyMethod = "close")
    fun userTransactionManager(): UserTransactionManager {
        val userTransactionManager = UserTransactionManager()
        userTransactionManager.setTransactionTimeout(300)
        userTransactionManager.forceShutdown = false
        return userTransactionManager
    }

    @Bean
    fun transactionManager(): JtaTransactionManager {
        val jtaTransactionManager = JtaTransactionManager()
        jtaTransactionManager.transactionManager =  userTransactionManager()
        jtaTransactionManager.userTransaction = userTransactionManager()
        return jtaTransactionManager
    }
}