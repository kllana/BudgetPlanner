package com.example.budget_plan.queue

interface MessageProcessor<T> {
    fun process(message: T)
}
