package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "budgets")
data class Budget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "budget_sum", nullable = false)
    val budgetSum: Double,

    @Column(name = "creation_date", nullable = false)
    val creationDate: LocalDate,

    @Column(name = "period", nullable = false)
    val period: LocalDate,

    @Column(name = "budget_amount")
    val budgetAmount: Double? = null,

    @Column(name = "budget_period")
    val budgetPeriod: LocalDate? = null
) {
    // Конструктор по умолчанию для JPA
    constructor() : this(null, 0.0, LocalDate.now(), LocalDate.now(), null, null)
}

