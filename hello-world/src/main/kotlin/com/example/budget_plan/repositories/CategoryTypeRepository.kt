package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryTypeRepository : JpaRepository<com.example.budget_plan.model.CategoryType, Int>

