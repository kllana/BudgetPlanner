package com.example.budget_plan.queue

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME = "my_queue" // Константное выражение
    }

    @Bean
    fun myQueue(): Queue {
        return Queue(QUEUE_NAME, true) // Создание очереди
    }
}
