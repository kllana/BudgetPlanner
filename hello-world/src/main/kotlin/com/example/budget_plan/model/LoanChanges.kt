package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "debt_changes")
data class LoanChanges(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_change_id")
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "debt_id", nullable = false)
    val loan: Loan?,

    @Column(name = "old_debt_value", nullable = false)
    val oldLoanValue: Double?,

    @Column(name = "new_debt_value", nullable = false)
    val newLoanValue: Double?,

    @Column(name = "debt_change_date", nullable = false)
    val changeDate: LocalDate?
) {
    constructor() : this(null, null, null, null, null) // Пустой конструктор для JPA
}
