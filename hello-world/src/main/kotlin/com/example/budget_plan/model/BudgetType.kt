package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "budget_type")
data class BudgetType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_type_id")
    val id: Int? = null,

    @Column(name = "budget_type_name", nullable = false)
    val budgetType: String? = null // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null)
}
