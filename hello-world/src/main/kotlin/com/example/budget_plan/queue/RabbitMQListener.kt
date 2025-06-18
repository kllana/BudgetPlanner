package com.example.budget_plan.queue

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class RabbitMQListener(
    private val stringMessageProcessor: StringMessageProcessor // Внедрение зависимости
) {

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME], autoStartup = "\${listener.enabled:true}")
    fun listen(message: String) {
        println("Получено сообщение: $message")
        stringMessageProcessor.process(message)
    }

}