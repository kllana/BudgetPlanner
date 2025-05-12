package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface BudgetTypeRepository : JpaRepository<com.example.budget_plan.model.BudgetType, Int>