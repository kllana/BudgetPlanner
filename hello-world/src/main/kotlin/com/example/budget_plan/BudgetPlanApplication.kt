package com.example.budget_plan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("com.example.budget_plan.model")
@EnableJpaRepositories("com.example.budget_plan.repositories")
class BudgetPlanApplication
fun main(args: Array<String>) {
	runApplication<com.example.budget_plan.BudgetPlanApplication>(*args)
}
	