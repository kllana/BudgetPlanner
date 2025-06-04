package com.example.budget_plan.security

import com.example.budget_plan.repositories.RoleRepository
import com.example.budget_plan.repositories.UserRepository
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val authService: AuthService,
    @Lazy private val userDetailsService: UserDetailsService,
    private val oAuthUserService: OAuthUserService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByUserName(username)
                .orElseThrow { UsernameNotFoundException("Пользователь не найден") }
            User.builder()
                .username(user.userName)
                .password(user.passwordHash)
                .roles(*user.roles.map { it.roleName }.toTypedArray())
                .build()
        }
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    // Включаем настройку OAuth2
    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(
            ClientRegistration.withRegistrationId("yandex")
                .clientId("84136536f6a147749415199276a1c4be")
                .clientSecret("ccdc5033d5ca4226be72aaa6b8b135e1")
                .scope("login:info")
                .userNameAttributeName("login")
                .authorizationUri("https://oauth.yandex.ru/authorize")
                .tokenUri("https://oauth.yandex.ru/token")
                .userInfoUri("https://login.yandex.ru/info")
                .redirectUri("http://localhost:8080/login/oauth2/code/yandex")
                .clientName("Yandex")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build()
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val configSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()

        corsConfig.allowedOrigins = listOf("http://127.0.0.1:5500")
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfig.allowedHeaders = listOf("Authorization", "Content-Type")
        corsConfig.allowCredentials = true

        configSource.registerCorsConfiguration("/**", corsConfig)

        http
            .cors { it.configurationSource(configSource) }
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.GET, "/login", "/logout", "/error").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login",  "/logout", "/auth/refresh").permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling { exceptions ->
                exceptions
                    .authenticationEntryPoint { request, response, authException ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Не авторизован")
                    }
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/login")
                    .successHandler { request, response, authentication ->
                        val principal = authentication.principal as? DefaultOAuth2User
                            ?: throw IllegalStateException("Principal is not an instance of DefaultOAuth2User")

                        val attributes = principal.attributes
                        val userName = attributes["login"] as? String ?: "unknown_user"

                        println("OAuth2 вход: $userName")

                        val user = oAuthUserService.processOAuthLogin(userName)

                        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it.roleName}") }

                        val newAuth = UsernamePasswordAuthenticationToken(
                            user.userName, null, authorities
                        )

                        SecurityContextHolder.getContext().authentication = newAuth

                        println("SecurityContext обновлён: ${SecurityContextHolder.getContext().authentication}")

                        response.sendRedirect("/hello")
                    }


                    .failureHandler { request, response, exception ->
                        println(" Ошибка OAuth2: ${exception.message}")
                        exception.printStackTrace()
                        response.sendRedirect("/login?error=true")
                    }
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
            }

        return http.build()
    }
}
