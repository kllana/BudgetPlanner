package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentsRepository : JpaRepository<com.example.budget_plan.model.Payments, Int>