package com.example.budget_plan.repositories

import com.example.budget_plan.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository : JpaRepository<Category, Int>