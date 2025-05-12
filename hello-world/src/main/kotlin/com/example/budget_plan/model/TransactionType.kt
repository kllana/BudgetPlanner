package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "transaction_type")  // изменено на соответствующее имя таблицы в БД
data class TransactionType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_type_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @Column(name = "transaction_type_name", nullable = false)  // изменено на соответствующее поле в БД
    val transactionType: String? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null)
}
