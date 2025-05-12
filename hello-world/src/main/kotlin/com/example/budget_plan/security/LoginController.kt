package com.example.budget_plan.security

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class LoginController {
    @GetMapping("/login")
    fun login(response: HttpServletResponse) {
        response.sendRedirect("/oauth2/authorization/yandex")
    }
}

