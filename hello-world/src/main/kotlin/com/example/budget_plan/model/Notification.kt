package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")  // изменено на соответствующее поле в БД
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // изменено на user_id
    val user: User? = null,  // Изменено на nullable

    @ManyToOne
    @JoinColumn(name = "notification_text_id", nullable = false)  // изменено на notification_text_id
    val notificationText: com.example.budget_plan.model.NotificationText? = null,  // Изменено на nullable

    @Column(name = "notification_creation_date", nullable = false)  // изменено на notification_creation_date
    val notificationCreationDate: LocalDate? = null  // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null, null, null)
}
