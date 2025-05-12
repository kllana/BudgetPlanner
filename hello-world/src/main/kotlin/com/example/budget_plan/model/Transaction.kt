package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "transactions")  // изменено на соответствующее имя таблицы в БД
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // изменено на соответствующее поле в БД
    val user: User? = null,  // Изменено на nullable

    @Column(name = "transaction_name", nullable = false)  // изменено на соответствующее поле в БД
    val transactionName: String? = null,  // Изменено на nullable

    @Column(name = "transaction_amount", nullable = false)  // изменено на соответствующее поле в БД
    val transactionAmount: Double? = null,  // Изменено на nullable

    @Column(name = "transaction_date", nullable = false)  // изменено на соответствующее поле в БД
    val transactionDate: LocalDate? = null,  // Изменено на nullable

    @Column(name = "transaction_description")  // изменено на соответствующее поле в БД
    val transactionDescription: String? = null,

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    val transactionType: TransactionType? = null,  // Изменено на nullable

    @ManyToOne
    @JoinColumn(name = "recurring_payment_id")
    val payment: Payments? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null, null, null, null, null, null)
}
