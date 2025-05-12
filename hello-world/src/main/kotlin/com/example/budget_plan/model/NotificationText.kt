package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "notification_texts")  // изменено на соответствующее имя таблицы в БД
data class NotificationText(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_text_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @Column(name = "notification_text", nullable = false)  // изменено на соответствующее поле в БД
    val notificationText: String? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null)
}
