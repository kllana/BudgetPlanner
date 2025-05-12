package com.example.budget_plan.security

import com.example.budget_plan.model.User
import com.example.budget_plan.repositories.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val userRepository: UserRepository, private val authService: AuthService) {

    // Логгер для этого класса
    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<Map<String, String>> {
        logger.info("Received login request for user: ${user.userName}")  // Логируем имя пользователя

        // Пытаемся найти пользователя по имени
        val foundUser = userRepository.findByUserName(user.userName)
            .orElseThrow {
                logger.warn("User with username ${user.userName} not found")  // Логируем предупреждение, если пользователь не найден
                RuntimeException("User not found")
            }

        // Логирование проверки пароля
        logger.debug("Checking password for user: ${user.userName}")

        // Проверка пароля (сравниваем хэш пароля)
        if (authService.checkPassword(user.passwordHash, foundUser.passwordHash)) {
            logger.info("User ${user.userName} successfully authenticated")  // Логируем успешную аутентификацию

            // Генерация JWT токена
            val token = authService.login(foundUser) // Используем метод login для генерации токена
            return ResponseEntity.ok(mapOf("token" to token))
        } else {
            logger.warn("Authentication failed for user: ${user.userName}")  // Логируем неудачную попытку входа
            return ResponseEntity.status(401).build() // Неверный пароль
        }
    }
}
