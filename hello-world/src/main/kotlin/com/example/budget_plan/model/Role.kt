package com.example.budget_plan.model


import jakarta.persistence.*

@Entity
@Table(name = "name_of_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    val id: Long? = null,  // Идентификатор роли, автоинкремент

    @Column(name = "name_of_role", nullable = false)
    val roleName: String  // Название роли
) {
    // Пустой конструктор для JPA
    constructor() : this(id = null, roleName = "")
}

