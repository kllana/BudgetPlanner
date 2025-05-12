package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<com.example.budget_plan.model.Loan, Int>