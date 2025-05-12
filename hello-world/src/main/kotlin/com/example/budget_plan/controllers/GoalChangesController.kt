package com.example.budget_plan.services

import com.example.budget_plan.model.GoalChanges
import com.example.budget_plan.repositories.GoalChangesRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@Service
class GoalChangesService(private val goalChangesRepository: GoalChangesRepository) {

    fun getAllGoalChanges(): List<GoalChanges> = goalChangesRepository.findAll()

    fun getGoalChangeById(id: Int): GoalChanges = goalChangesRepository.findById(id).orElseThrow {
        RuntimeException("GoalChange not found with id: $id")
    }

    fun createGoalChange(goalChange: GoalChanges): GoalChanges = goalChangesRepository.save(goalChange)

    fun updateGoalChange(id: Int, updatedGoalChange: GoalChanges): GoalChanges {
        val existingGoalChange = getGoalChangeById(id)
        val updated = existingGoalChange.copy(
            goal = updatedGoalChange.goal,
            oldGoalValue = updatedGoalChange.oldGoalValue,
            newGoalValue = updatedGoalChange.newGoalValue,
            changeDate = updatedGoalChange.changeDate
        )
        return goalChangesRepository.save(updated)
    }

    fun deleteGoalChange(id: Int) {
        if (!goalChangesRepository.existsById(id)) {
            throw RuntimeException("GoalChange not found with id: $id")
        }
        goalChangesRepository.deleteById(id)
    }
}


@RestController
@RequestMapping("/goalChanges")
class GoalChangesController(private val goalChangesService: GoalChangesService) {

    @GetMapping
    fun getAllGoalChanges(): List<GoalChanges> = goalChangesService.getAllGoalChanges()

    @GetMapping("/{id}")
    fun getGoalChangeById(@PathVariable id: Int): GoalChanges = goalChangesService.getGoalChangeById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGoalChange(@RequestBody goalChange: GoalChanges): GoalChanges =
        goalChangesService.createGoalChange(goalChange)

    @PutMapping("/{id}")
    fun updateGoalChange(@PathVariable id: Int, @RequestBody updatedGoalChange: GoalChanges): GoalChanges =
        goalChangesService.updateGoalChange(id, updatedGoalChange)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGoalChange(@PathVariable id: Int) = goalChangesService.deleteGoalChange(id)
}
