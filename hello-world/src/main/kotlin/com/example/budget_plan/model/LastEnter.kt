package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "last_login")
data class LastEnter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "last_login_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @Column(name = "last_login_date", nullable = false)  // изменено на соответствующее поле в БД
    val lastEnterDate: java.time.LocalDate? = null,  // Изменено на nullable

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // изменено на user_id
    val user: User? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null)
}
