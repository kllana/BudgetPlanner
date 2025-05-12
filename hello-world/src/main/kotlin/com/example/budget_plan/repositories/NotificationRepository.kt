package com.example.budget_plan.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<com.example.budget_plan.model.Notification, Int>