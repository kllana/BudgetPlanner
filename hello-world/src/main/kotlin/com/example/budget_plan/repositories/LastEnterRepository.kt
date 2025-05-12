package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface LastEnterRepository : JpaRepository<com.example.budget_plan.model.LastEnter, Int>