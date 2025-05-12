package com.example.budget_plan.security


import com.example.budget_plan.model.Role
import com.example.budget_plan.model.User
import com.example.budget_plan.repositories.RoleRepository
import com.example.budget_plan.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class OAuthUserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    fun processOAuthLogin(userName: String): User {
        val existingUser = userRepository.findByUserName(userName)
        if (existingUser.isPresent) {
            return existingUser.get()
        }

        val role = roleRepository.findByRoleName("USER")
            ?: roleRepository.save(Role(roleName = "USER"))

        val newUser = User(
            userName = userName,
            passwordHash = "",
            mail = "$userName@yandex.ru",
            roles = setOf(role)
        )

        return userRepository.save(newUser)
    }
}
