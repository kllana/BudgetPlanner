package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionTypeRepository : JpaRepository<com.example.budget_plan.model.TransactionType, Int>