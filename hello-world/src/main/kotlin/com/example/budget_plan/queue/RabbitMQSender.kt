package com.example.budget_plan.queue

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMQSender(
    private val rabbitTemplate: RabbitTemplate
) {

    fun sendMessage(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message)
        println("Сообщение отправлено: $message")
    }
}
