package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "debts")
data class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // изменено на user_id
    val user: User? = null,  // Изменено на nullable

    @Column(name = "debt_name", nullable = false)  // изменено на debt_name
    val loanName: String? = null,  // Изменено на nullable

    @Column(name = "debt_amount", nullable = false)  // изменено на debt_amount
    val loanSum: Double? = null,  // Изменено на nullable

    @Column(name = "debt_due_date", nullable = false)  // изменено на debt_due_date
    val loanDeadline: LocalDate? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null, null, null)
}
