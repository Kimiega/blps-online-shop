package com.kimiega.onlineshop.config

import org.springframework.boot.context.properties.ConfigurationProperties
@ConfigurationProperties("db1")
data class Database1(
    val url: String,
    val username: String,
    val password: String,
)

@ConfigurationProperties("db2")
data class Database2(
    val url: String,
    val username: String,
    val password: String,
)
