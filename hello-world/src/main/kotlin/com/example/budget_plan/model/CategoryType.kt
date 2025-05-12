package com.example.budget_plan.model

import jakarta.persistence.*

@Entity
@Table(name = "categories_type")
data class CategoryType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_type_id")
    val id: Int? = null,

    @Column(name = "category_type_name", nullable = false)
    val categoryType: String? = null // Изменено на nullable
) {
    // Пустой конструктор для JPA
    constructor() : this(null, null)
}
