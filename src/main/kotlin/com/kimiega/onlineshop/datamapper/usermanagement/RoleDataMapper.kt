package com.kimiega.onlineshop.datamapper.usermanagement

import jakarta.persistence.*

@Entity(name = "roles")
data class RoleDataMapper(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(name = "name", unique = true, nullable = false)
    val name: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_privileges",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id")]
    )
    val privileges: Collection<PrivilegeDataMapper>? = mutableListOf()
) {
    @ManyToMany(mappedBy = "roles")
    private val users: Collection<UserDataMapper>? = mutableListOf()
}
