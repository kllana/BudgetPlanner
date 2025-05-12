package com.example.budget_plan.controllers

import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class LastEnterService(private val lastEnterRepository: com.example.budget_plan.repositories.LastEnterRepository) {

    fun getAllLastEnters(): List<com.example.budget_plan.model.LastEnter> = lastEnterRepository.findAll()

    fun getLastEnterById(id: Int): com.example.budget_plan.model.LastEnter = lastEnterRepository.findById(id).orElseThrow {
        RuntimeException("LastEnter not found with id: $id")
    }

    fun createLastEnter(lastEnter: com.example.budget_plan.model.LastEnter): com.example.budget_plan.model.LastEnter = lastEnterRepository.save(lastEnter)

    fun updateLastEnter(id: Int, updatedLastEnter: com.example.budget_plan.model.LastEnter): com.example.budget_plan.model.LastEnter {
        val existingLastEnter = getLastEnterById(id)
        val updated = existingLastEnter.copy(
            lastEnterDate = updatedLastEnter.lastEnterDate,
            user = updatedLastEnter.user
        )
        return lastEnterRepository.save(updated)
    }

    fun deleteLastEnter(id: Int) {
        if (!lastEnterRepository.existsById(id)) {
            throw RuntimeException("LastEnter not found with id: $id")
        }
        lastEnterRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/lastEnter")
class LastEnterController(private val lastEnterService: com.example.budget_plan.controllers.LastEnterService) {

    @GetMapping
    fun getAllLastEnters(): List<com.example.budget_plan.model.LastEnter> = lastEnterService.getAllLastEnters()

    @GetMapping("/{id}")
    fun getLastEnterById(@PathVariable id: Int): com.example.budget_plan.model.LastEnter = lastEnterService.getLastEnterById(id)

    @PostMapping
    fun createLastEnter(@RequestBody lastEnter: com.example.budget_plan.model.LastEnter): com.example.budget_plan.model.LastEnter = lastEnterService.createLastEnter(lastEnter)

    @PutMapping("/{id}")
    fun updateLastEnter(@PathVariable id: Int, @RequestBody updatedLastEnter: com.example.budget_plan.model.LastEnter): com.example.budget_plan.model.LastEnter =
        lastEnterService.updateLastEnter(id, updatedLastEnter)

    @DeleteMapping("/{id}")
    fun deleteLastEnter(@PathVariable id: Int) = lastEnterService.deleteLastEnter(id)
}
