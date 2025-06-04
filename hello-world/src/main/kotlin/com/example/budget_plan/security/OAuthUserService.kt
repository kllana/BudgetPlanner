package com.example.budget_plan.security


import com.example.budget_plan.model.Role
import com.example.budget_plan.model.User
import com.example.budget_plan.repositories.RoleRepository
import com.example.budget_plan.repositories.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OAuthUserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(OAuthUserService::class.java)
    }


    fun processOAuthLogin(userName: String): User {
        logger.info("OAuth вход пользователя: $userName")

        val existingUser = userRepository.findByUserName(userName)
        logger.info("Пользователь $userName найден в базе данных.")

        if (existingUser.isPresent) {
            return existingUser.get()
        }

        logger.info("Пользователь $userName не найден. Создание нового пользователя.")
        val role = roleRepository.findByRoleName("USER")
            ?: run {
                logger.info("Роль USER не найдена. Сохраняем новую роль.")
                roleRepository.save(Role(roleName = "USER"))
            }

        val newUser = User(
            userName = userName,
            passwordHash = "",
            mail = "$userName@yandex.ru",
            roles = setOf(role)
        )

        val savedUser = userRepository.save(newUser)
        logger.info("Создан новый пользователь: ${savedUser.userName} с email: ${savedUser.mail}")

        return savedUser
    }
}