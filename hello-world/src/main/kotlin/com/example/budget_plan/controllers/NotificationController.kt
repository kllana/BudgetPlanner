package com.example.budget_plan.controllers
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class NotificationService(private val notificationRepository: com.example.budget_plan.repositories.NotificationRepository) {

    fun getAllNotifications(): List<com.example.budget_plan.model.Notification> = notificationRepository.findAll()

    fun getNotificationById(id: Int): com.example.budget_plan.model.Notification = notificationRepository.findById(id).orElseThrow {
        RuntimeException("Notification not found with id: $id")
    }

    fun createNotification(notification: com.example.budget_plan.model.Notification): com.example.budget_plan.model.Notification = notificationRepository.save(notification)

    fun updateNotification(id: Int, updatedNotification: com.example.budget_plan.model.Notification): com.example.budget_plan.model.Notification {
        val existingNotification = getNotificationById(id)
        val updated = existingNotification.copy(
            user = updatedNotification.user,
            notificationText = updatedNotification.notificationText,
            notificationCreationDate = updatedNotification.notificationCreationDate
        )
        return notificationRepository.save(updated)
    }

    fun deleteNotification(id: Int) {
        if (!notificationRepository.existsById(id)) {
            throw RuntimeException("Notification not found with id: $id")
        }
        notificationRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/notification")
class NotificationController(private val notificationService: com.example.budget_plan.controllers.NotificationService) {

    @GetMapping
    fun getAllNotifications(): List<com.example.budget_plan.model.Notification> = notificationService.getAllNotifications()

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Int): com.example.budget_plan.model.Notification = notificationService.getNotificationById(id)

    @PostMapping
    fun createNotification(@RequestBody notification: com.example.budget_plan.model.Notification): com.example.budget_plan.model.Notification =
        notificationService.createNotification(notification)

    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Int, @RequestBody updatedNotification: com.example.budget_plan.model.Notification): com.example.budget_plan.model.Notification =
        notificationService.updateNotification(id, updatedNotification)

    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Int) = notificationService.deleteNotification(id)
}
