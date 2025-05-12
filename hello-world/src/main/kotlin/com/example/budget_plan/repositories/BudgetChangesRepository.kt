package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BudgetChangesRepository : JpaRepository<com.example.budget_plan.model.BudgetChanges, Int>