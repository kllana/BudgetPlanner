package com.example.budget_plan.controllers

import com.example.budget_plan.model.BudgetChanges
import com.example.budget_plan.repositories.BudgetChangesRepository
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class BudgetChangesService(private val budgetChangesRepository: BudgetChangesRepository) {

    fun getAllBudgetChanges(): List<BudgetChanges> = budgetChangesRepository.findAll()

    fun getBudgetChangeById(id: Int): BudgetChanges = budgetChangesRepository.findById(id).orElseThrow {
        RuntimeException("BudgetChange not found with id: $id")
    }

    fun createBudgetChange(budgetChange: BudgetChanges): BudgetChanges = budgetChangesRepository.save(budgetChange)

    fun updateBudgetChange(id: Int, updatedBudgetChange: BudgetChanges): BudgetChanges {
        val existingBudgetChange = getBudgetChangeById(id)
        val updated = existingBudgetChange.copy(
            budget = updatedBudgetChange.budget,
            oldBudgetValue = updatedBudgetChange.oldBudgetValue,
            newBudgetValue = updatedBudgetChange.newBudgetValue,
            changeDate = updatedBudgetChange.changeDate
        )
        return budgetChangesRepository.save(updated)
    }

    fun deleteBudgetChange(id: Int) {
        if (!budgetChangesRepository.existsById(id)) {
            throw RuntimeException("BudgetChange not found with id: $id")
        }
        budgetChangesRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/budgetChanges")
class BudgetChangesController(private val budgetChangesService: BudgetChangesService) {

    @GetMapping
    fun getAllBudgetChanges(): List<BudgetChanges> = budgetChangesService.getAllBudgetChanges()

    @GetMapping("/{id}")
    fun getBudgetChangeById(@PathVariable id: Int): BudgetChanges = budgetChangesService.getBudgetChangeById(id)

    @PostMapping
    fun createBudgetChange(@RequestBody budgetChange: BudgetChanges): BudgetChanges =
        budgetChangesService.createBudgetChange(budgetChange)

    @PutMapping("/{id}")
    fun updateBudgetChange(@PathVariable id: Int, @RequestBody updatedBudgetChange: BudgetChanges): BudgetChanges =
        budgetChangesService.updateBudgetChange(id, updatedBudgetChange)

    @DeleteMapping("/{id}")
    fun deleteBudgetChange(@PathVariable id: Int) = budgetChangesService.deleteBudgetChange(id)
}
