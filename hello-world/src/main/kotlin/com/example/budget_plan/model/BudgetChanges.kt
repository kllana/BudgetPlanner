package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "budget_changes")
data class BudgetChanges(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_change_id")
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    val budget: Budget? = null,  // Сделано nullable для пустого конструктора

    @Column(name = "old_budget_value", nullable = false)
    val oldBudgetValue: Double? = null,  // Сделано nullable

    @Column(name = "new_budget_value", nullable = false)
    val newBudgetValue: Double? = null,  // Сделано nullable

    @Column(name = "budget_change_date", nullable = false)
    val changeDate: LocalDate? = null  // Сделано nullable
) {
    constructor() : this(null, null, null, null, null)
}
