package com.kimiega.onlineshop.entity

data class AppRole(
    val id: Long,
    val name: String,
    val privileges: Collection<Privilege>
)
