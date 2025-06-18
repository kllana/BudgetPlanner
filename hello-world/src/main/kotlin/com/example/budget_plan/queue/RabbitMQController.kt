package com.example.budget_plan.queue

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RabbitMQController(
    private val rabbitMQSender: RabbitMQSender
) {

    @GetMapping("/send")
    fun sendMessage(@RequestParam message: String): String {
        rabbitMQSender.sendMessage(message)
        return "Сообщение отправлено: $message"
    }
}
