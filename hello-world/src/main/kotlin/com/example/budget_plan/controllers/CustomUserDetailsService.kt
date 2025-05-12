/*package com.example.budget_plan.security

import com.example.budget_plan.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByMail(email)
            ?: throw UsernameNotFoundException("Пользователь с email $email не найден")
        return CustomUserDetails(user)
    }
}




@RestController
@RequestMapping("/user")
class SUserController {
    @GetMapping
    fun userHome(): String {
        return "Добро пожаловать, пользователь!"
    }
}


@RestController
@RequestMapping("/premium")
class SPremiumController {
    @GetMapping
    fun premiumHome(): String {
        return "Добро пожаловать, премиум-пользователь!"
    }
}
        */