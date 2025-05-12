package com.example.budget_plan.controllers
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service
import com.example.budget_plan.model.Budget
import com.example.budget_plan.repositories.BudgetRepository
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


@Service
class BudgetService(private val budgetRepository: BudgetRepository) {

    fun getAllBudgets(): List<Budget> = budgetRepository.findAll()

    fun getBudgetById(id: Int): Budget =
        budgetRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found with ID: $id")
        }

    fun createBudget(budget: Budget): Budget = budgetRepository.save(budget)

    fun updateBudget(id: Int, updatedBudget: Budget): Budget {
        val existingBudget = getBudgetById(id)
        val updated = existingBudget.copy(
            creationDate = updatedBudget.creationDate,
            period = updatedBudget.period,
            budgetSum = updatedBudget.budgetSum
        )
        return budgetRepository.save(updated)
    }

    fun deleteBudget(id: Int) {
        if (!budgetRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found with ID: $id")
        }
        budgetRepository.deleteById(id)
    }
}
@RestController
@RequestMapping("/budgets")
class BudgetController(private val budgetService: BudgetService) {

    @GetMapping
    fun getAllBudgets(): List<Budget> = budgetService.getAllBudgets()

    @GetMapping("/{id}")
    fun getBudgetById(@PathVariable id: Int): Budget = budgetService.getBudgetById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBudget(@RequestBody budget: Budget): Budget = budgetService.createBudget(budget)

    @PutMapping("/{id}")
    fun updateBudget(@PathVariable id: Int, @RequestBody updatedBudget: Budget): Budget =
        budgetService.updateBudget(id, updatedBudget)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBudget(@PathVariable id: Int) = budgetService.deleteBudget(id)
}