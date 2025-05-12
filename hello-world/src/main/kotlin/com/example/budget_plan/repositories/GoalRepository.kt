package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface GoalRepository : JpaRepository<com.example.budget_plan.model.Goal, Int>