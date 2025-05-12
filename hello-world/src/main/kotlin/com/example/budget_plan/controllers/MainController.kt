package com.example.budget_plan.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class   MainController {
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello, world!"
    }
}


