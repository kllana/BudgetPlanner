package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "goals")
data class Goal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // изменено на user_id
    val user: User? = null,  // Изменено на nullable

    @Column(name = "goal_name", nullable = false)  // изменено на goal_name
    val goalName: String? = null,  // Изменено на nullable

    @Column(name = "goal_amount", nullable = false)  // изменено на goal_amount
    val goalSum: Double? = null,  // Изменено на nullable

    @Column(name = "goal_due_date", nullable = false)  // изменено на goal_due_date
    val goalDeadline: LocalDate? = null,  // Изменено на nullable

    @Column(name = "goal_creation_date", nullable = false)  // изменено на goal_creation_date
    val goalCreationDate: LocalDate? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null, null, null, null)
}
