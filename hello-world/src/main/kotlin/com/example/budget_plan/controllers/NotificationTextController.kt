package com.example.budget_plan.controllers

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class NotificationTextService(private val notificationTextRepository: com.example.budget_plan.repositories.NotificationTextRepository) {

    fun getAllNotificationTexts(): List<com.example.budget_plan.model.NotificationText> = notificationTextRepository.findAll()

    fun getNotificationTextById(id: Int): com.example.budget_plan.model.NotificationText = notificationTextRepository.findById(id).orElseThrow {
        RuntimeException("NotificationText not found with id: $id")
    }

    fun createNotificationText(notificationText: com.example.budget_plan.model.NotificationText): com.example.budget_plan.model.NotificationText =
        notificationTextRepository.save(notificationText)

    fun updateNotificationText(id: Int, updatedNotificationText: com.example.budget_plan.model.NotificationText): com.example.budget_plan.model.NotificationText {
        val existingText = getNotificationTextById(id)
        val updated = existingText.copy(notificationText = updatedNotificationText.notificationText)
        return notificationTextRepository.save(updated)
    }

    fun deleteNotificationText(id: Int) {
        if (!notificationTextRepository.existsById(id)) {
            throw RuntimeException("NotificationText not found with id: $id")
        }
        notificationTextRepository.deleteById(id)
    }
}


@RestController
@RequestMapping("/notification-texts")
class NotificationTextController(private val notificationTextService: com.example.budget_plan.controllers.NotificationTextService) {

    @GetMapping
    fun getAllNotificationTexts() = notificationTextService.getAllNotificationTexts()

    @GetMapping("/{id}")
    fun getNotificationTextById(@PathVariable id: Int) = notificationTextService.getNotificationTextById(id)

    @PostMapping
    fun createNotificationText(@RequestBody notificationText: com.example.budget_plan.model.NotificationText) = notificationTextService.createNotificationText(notificationText)

    @PutMapping("/{id}")
    fun updateNotificationText(@PathVariable id: Int, @RequestBody notificationText: com.example.budget_plan.model.NotificationText) = notificationTextService.updateNotificationText(id, notificationText)

    @DeleteMapping("/{id}")
    fun deleteNotificationText(@PathVariable id: Int) = notificationTextService.deleteNotificationText(id)
}
