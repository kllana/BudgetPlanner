package com.example.budget_plan.controllers
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class GoalService(private val goalRepository: com.example.budget_plan.repositories.GoalRepository) {

    fun getAllGoals(): List<com.example.budget_plan.model.Goal> = goalRepository.findAll()

    fun getGoalById(id: Int): com.example.budget_plan.model.Goal = goalRepository.findById(id).orElseThrow {
        RuntimeException("Goal not found with id: $id")
    }

    fun createGoal(goal: com.example.budget_plan.model.Goal): com.example.budget_plan.model.Goal = goalRepository.save(goal)

    fun updateGoal(id: Int, updatedGoal: com.example.budget_plan.model.Goal): com.example.budget_plan.model.Goal {
        val existingGoal = getGoalById(id)
        val updated = existingGoal.copy(
            user = updatedGoal.user,
            goalName = updatedGoal.goalName,
            goalSum = updatedGoal.goalSum,
            goalDeadline = updatedGoal.goalDeadline,
            goalCreationDate = updatedGoal.goalCreationDate
        )
        return goalRepository.save(updated)
    }

    fun deleteGoal(id: Int) {
        if (!goalRepository.existsById(id)) {
            throw RuntimeException("Goal not found with id: $id")
        }
        goalRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/goal")
class GoalController(private val goalService: com.example.budget_plan.controllers.GoalService) {

    @GetMapping
    fun getAllGoals(): List<com.example.budget_plan.model.Goal> = goalService.getAllGoals()

    @GetMapping("/{id}")
    fun getGoalById(@PathVariable id: Int): com.example.budget_plan.model.Goal = goalService.getGoalById(id)

    @PostMapping
    fun createGoal(@RequestBody goal: com.example.budget_plan.model.Goal): com.example.budget_plan.model.Goal = goalService.createGoal(goal)

    @PutMapping("/{id}")
    fun updateGoal(@PathVariable id: Int, @RequestBody updatedGoal: com.example.budget_plan.model.Goal): com.example.budget_plan.model.Goal =
        goalService.updateGoal(id, updatedGoal)

    @DeleteMapping("/{id}")
    fun deleteGoal(@PathVariable id: Int) = goalService.deleteGoal(id)
}
