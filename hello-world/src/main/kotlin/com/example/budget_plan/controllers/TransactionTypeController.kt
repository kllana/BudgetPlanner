package com.example.budget_plan.controllers

import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class TransactionTypeService(private val transactionTypeRepository: com.example.budget_plan.repositories.TransactionTypeRepository) {

    fun getAllTransactionTypes(): List<com.example.budget_plan.model.TransactionType> = transactionTypeRepository.findAll()

    fun getTransactionTypeById(id: Int): com.example.budget_plan.model.TransactionType = transactionTypeRepository.findById(id).orElseThrow {
        RuntimeException("TransactionType not found with id: $id")
    }

    fun createTransactionType(transactionType: com.example.budget_plan.model.TransactionType): com.example.budget_plan.model.TransactionType =
        transactionTypeRepository.save(transactionType)

    fun updateTransactionType(id: Int, updatedTransactionType: com.example.budget_plan.model.TransactionType): com.example.budget_plan.model.TransactionType {
        val existingType = getTransactionTypeById(id)
        val updated = existingType.copy(transactionType = updatedTransactionType.transactionType)
        return transactionTypeRepository.save(updated)
    }

    fun deleteTransactionType(id: Int) {
        if (!transactionTypeRepository.existsById(id)) {
            throw RuntimeException("TransactionType not found with id: $id")
        }
        transactionTypeRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/transaction-type")
class TransactionTypeController(private val transactionTypeService: com.example.budget_plan.controllers.TransactionTypeService) {

    @GetMapping
    fun getAllTransactionTypes() = transactionTypeService.getAllTransactionTypes()

    @GetMapping("/{id}")
    fun getTransactionTypeById(@PathVariable id: Int) = transactionTypeService.getTransactionTypeById(id)

    @PostMapping
    fun createTransactionType(@RequestBody transactionType: com.example.budget_plan.model.TransactionType) = transactionTypeService.createTransactionType(transactionType)

    @PutMapping("/{id}")
    fun updateTransactionType(@PathVariable id: Int, @RequestBody transactionType: com.example.budget_plan.model.TransactionType) = transactionTypeService.updateTransactionType(id, transactionType)

    @DeleteMapping("/{id}")
    fun deleteTransactionType(@PathVariable id: Int) = transactionTypeService.deleteTransactionType(id)
}