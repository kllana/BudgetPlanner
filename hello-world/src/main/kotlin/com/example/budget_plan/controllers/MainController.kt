package com.example.budget_plan.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class   MainController {
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello, world!"
    }
}


