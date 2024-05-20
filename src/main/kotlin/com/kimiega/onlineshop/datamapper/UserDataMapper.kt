package com.kimiega.onlineshop.datamapper

import jakarta.persistence.*


@Entity(name = "users")
data class UserDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "username", unique = true, nullable = false)
    val username: String? = null,
    @Column(name = "email", unique = false, nullable = false)
    val email: String? = null,
    @Column(name = "password", unique = false, nullable = false)
    val password: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: Collection<RoleDataMapper>? = mutableListOf()
) {
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "user")
    val listOfOrders : List<OrderDataMapper>? = mutableListOf()
}
