package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<com.example.budget_plan.model.Transaction, Int>