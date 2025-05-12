package com.example.budget_plan.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "goal_changes")
data class GoalChanges(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_change_id")
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    val goal: Goal?,

    @Column(name = "old_goal_value", nullable = false)
    val oldGoalValue: Double?,

    @Column(name = "new_goal_value", nullable = false)
    val newGoalValue: Double?,

    @Column(name = "goal_change_date", nullable = false)
    val changeDate: LocalDate?
) {
    constructor() : this(null, null, null, null, null) // Пустой конструктор для JPA
}
