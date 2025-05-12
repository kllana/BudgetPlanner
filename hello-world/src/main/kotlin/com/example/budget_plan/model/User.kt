package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")  // таблица пользователей
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // поле идентификатора пользователя
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    val userName: String, // Изменено на nullable

    @Column(name = "email", nullable = true)
    val mail: String? = null,  // Изменено на nullable

    @Column(name = "password_hash", nullable = false)
    val passwordHash: String,

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    val role: Role? = null,

    @OneToOne
    @JoinColumn(name = "last_login_id")
    val lastEnter: LastEnter? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles", // таблица для связи между пользователями и ролями
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = emptySet()

) {

    constructor() : this(id = null, userName = "", passwordHash = "", mail = "", roles = emptySet())

}
