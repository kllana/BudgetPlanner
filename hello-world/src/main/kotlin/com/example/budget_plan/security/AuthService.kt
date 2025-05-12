package com.example.budget_plan.security

import com.example.budget_plan.model.User
import com.example.budget_plan.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.crypto.SecretKey

@Service
class AuthService(private val userRepository: UserRepository) {

    // Секретный ключ для подписи токенов
    private val jwtSecret: String = "yourSuperSecretKeyThatIsLongEnoughToBeSecure"

    // Метод для получения SecretKey
    private fun getSigningKey(): SecretKey {
        return Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    // Логгер для сервиса
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)
    }

    // Генерация токена для пользователя
    fun login(user: User): String {
        logger.info("Пытаемся выполнить вход для пользователя: ${user.userName}")

        // Получаем список ролей пользователя
        val roles = user.roles.mapNotNull { it.roleName }

        // Генерируем JWT токен для пользователя
        val token = generateJwtToken(user.userName, roles)

        logger.info("Токен успешно сгенерирован для пользователя: ${user.userName}")
        return token
    }

    // Генерация нового токена (например, для refresh токена)
    fun refreshToken(refreshToken: String): String {
        // Здесь можно добавить валидацию refreshToken перед генерацией нового
        logger.info("Генерация нового refresh токена")
        return generateJwtToken("username", listOf("ROLE_USER"))
    }

    // Приватный метод для генерации JWT токена
    private fun generateJwtToken(username: String, roles: List<String>): String {
        val now = Date()
        val validity = Date(now.time + 86400000) // Токен истекает через 1 день

        // Генерация JWT с использованием подписи HMAC и секретного ключа
        return Jwts.builder()
            .setSubject(username) // Subject токена
            .claim("roles", roles) // Добавляем роли в claims
            .setIssuedAt(now) // Время выпуска токена
            .setExpiration(validity) // Устанавливаем время истечения
            .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Подписываем с использованием SecretKey
            .compact() // Генерируем строку токена
    }
/*
    // Проверка и валидация токена
    fun validateJwtToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser()
                .setSigningKey(getSigningKey()) // Устанавливаем секретный ключ для проверки подписи
                .build()
                .parseClaimsJws(token)

            // Проверяем срок действия токена
            claims.body.expiration.after(Date())
        } catch (e: Exception) {
            logger.error("Ошибка валидации JWT токена: ${e.message}")
            false
        }
    }

    // Извлечение имени пользователя из токена
    fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)

        return claims.body.subject // Извлекаем имя пользователя (subject)
    }
*/
    // Проверка пароля (с использованием BCrypt)
    fun checkPassword(enteredPassword: String, storedPassword: String): Boolean {
        logger.debug("Проверка пароля для введённого: $enteredPassword")
        return BCrypt.checkpw(enteredPassword, storedPassword)
    }

    // Хэширование пароля с использованием BCrypt (для регистрации)
    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}
