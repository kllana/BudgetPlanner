package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface LoanChangesRepository : JpaRepository<com.example.budget_plan.model.LoanChanges, Int>