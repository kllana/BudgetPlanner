package com.example.budget_plan.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LogoutController {

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().authentication)

        return "redirect:/"
    }
}
