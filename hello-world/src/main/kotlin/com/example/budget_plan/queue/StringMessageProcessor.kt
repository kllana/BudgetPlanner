package com.example.budget_plan.queue

import org.springframework.stereotype.Component

@Component
class StringMessageProcessor : MessageProcessor<String> {
    override fun process(message: String) {
        println("Обработка строки: $message")
    }
}
