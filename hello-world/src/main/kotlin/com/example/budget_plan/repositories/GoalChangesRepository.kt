package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface GoalChangesRepository : JpaRepository<com.example.budget_plan.model.GoalChanges, Int>