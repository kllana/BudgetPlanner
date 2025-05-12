package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Int? = null,

    @Column(name = "category_name", nullable = false)
    val categoryName: String? = null
) {
    constructor() : this(null, null)
}
