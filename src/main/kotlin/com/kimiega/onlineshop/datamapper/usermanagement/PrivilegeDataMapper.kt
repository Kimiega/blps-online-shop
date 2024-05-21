package com.kimiega.onlineshop.datamapper.usermanagement

import jakarta.persistence.*


@Entity(name = "privileges")
data class PrivilegeDataMapper(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(name = "name", unique = true, nullable = false)
    val name: String? = null,
) {
    @ManyToMany(mappedBy = "privileges")
    private val roles: Collection<RoleDataMapper>? = mutableListOf()
}
