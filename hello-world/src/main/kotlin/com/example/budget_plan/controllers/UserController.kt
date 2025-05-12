package com.example.budget_plan.controllers

import com.example.budget_plan.model.User
import com.example.budget_plan.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import com.example.budget_plan.repositories.RoleRepository


@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User = userRepository.findById(id).orElseThrow {
        RuntimeException("User not found with id: $id")
    }

    fun createUser(user: User, roleName: String): User {
        val role = roleRepository.findByRoleName(roleName)
            ?: throw RuntimeException("Role not found: $roleName")
        val userWithRole = user.copy(role = role)
        return userRepository.save(userWithRole)
    }

    fun updateUser(id: Long, updatedUser: User, roleName: String): User {
        val existingUser = getUserById(id)
        val role = roleRepository.findByRoleName(roleName)
            ?: throw RuntimeException("Role not found: $roleName")

        val updated = existingUser.copy(
            userName = updatedUser.userName,
            mail = updatedUser.mail,
            passwordHash = updatedUser.passwordHash,
            //accountCreationDate = updatedUser.accountCreationDate,
            role = role
        )
        return userRepository.save(updated)
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw RuntimeException("User not found with id: $id")
        }
        userRepository.deleteById(id)
    }
}


@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers() = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long) = userService.getUserById(id)

    @PostMapping
    fun createUser(@RequestBody user: User, @RequestParam roleName: String) =
        userService.createUser(user, roleName)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User, @RequestParam roleName: String) =
        userService.updateUser(id, user, roleName)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}

