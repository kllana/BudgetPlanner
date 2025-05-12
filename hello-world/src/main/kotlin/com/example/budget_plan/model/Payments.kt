package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "recurring_payments")  // изменено на соответствующее имя таблицы в БД
data class Payments(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recurring_payment_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @Column(name = "recurring_payment_amount", nullable = false)  // изменено на соответствующее поле в БД
    val paymentSum: Double? = null,  // Изменено на nullable

    @Column(name = "recurring_payment_period", nullable = false)  // изменено на соответствующее поле в БД
    val paymentPeriod: String? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null)
}
