package com.example.budget_plan.controllers;

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class BudgetTypeService(private val budgetTypeRepository: com.example.budget_plan.repositories.BudgetTypeRepository) {

    fun getAllBudgetTypes(): List<com.example.budget_plan.model.BudgetType> = budgetTypeRepository.findAll()

    fun getBudgetTypeById(id: Int): com.example.budget_plan.model.BudgetType = budgetTypeRepository.findById(id).orElseThrow {
        RuntimeException("BudgetType not found with id: $id")
    }

    fun createBudgetType(budgetType: com.example.budget_plan.model.BudgetType): com.example.budget_plan.model.BudgetType = budgetTypeRepository.save(budgetType)

    fun updateBudgetType(id: Int, updatedBudgetType: com.example.budget_plan.model.BudgetType): com.example.budget_plan.model.BudgetType {
        val existingType = getBudgetTypeById(id)
        val updated = existingType.copy(budgetType = updatedBudgetType.budgetType)
        return budgetTypeRepository.save(updated)
    }

    fun deleteBudgetType(id: Int) {
        if (!budgetTypeRepository.existsById(id)) {
            throw RuntimeException("BudgetType not found with id: $id")
        }
        budgetTypeRepository.deleteById(id)
    }
}
@RestController
@RequestMapping("/budget-types")
class BudgetTypeController(private val budgetTypeService: com.example.budget_plan.controllers.BudgetTypeService) {

    @GetMapping
    fun getAllBudgetTypes() = budgetTypeService.getAllBudgetTypes()

    @GetMapping("/{id}")
    fun getBudgetTypeById(@PathVariable id: Int) = budgetTypeService.getBudgetTypeById(id)

    @PreAuthorize("hasRole('PREMIUM')")
    @PostMapping
    fun createBudgetType(@RequestBody budgetType: com.example.budget_plan.model.BudgetType) = budgetTypeService.createBudgetType(budgetType)

    @PreAuthorize("hasRole('PREMIUM')")
    @PutMapping("/{id}")
    fun updateBudgetType(@PathVariable id: Int, @RequestBody budgetType: com.example.budget_plan.model.BudgetType) = budgetTypeService.updateBudgetType(id, budgetType)

    @PreAuthorize("hasRole('PREMIUM')")
    @DeleteMapping("/{id}")
    fun deleteBudgetType(@PathVariable id: Int) = budgetTypeService.deleteBudgetType(id)
}
