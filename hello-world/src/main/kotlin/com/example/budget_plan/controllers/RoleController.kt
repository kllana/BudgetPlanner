package com.example.budget_plan.controllers

import com.example.budget_plan.model.Role
import com.example.budget_plan.repositories.RoleRepository
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Service
class RoleService(private val roleRepository: RoleRepository) {

    // Получить все роли
    fun getAllRoles(): List<Role> {
        return roleRepository.findAll()
    }

    // Найти роль по ID
    fun getRoleById(id: Long): Optional<Role> {
        return roleRepository.findById(id)
    }

    // Создать новую роль
    fun createRole(role: Role): Role {
        return roleRepository.save(role)
    }

    // Обновить существующую роль
    fun updateRole(id: Long, updatedRole: Role): Role {
        val existingRole = roleRepository.findById(id).orElseThrow {
            IllegalArgumentException("Role with id $id not found")
        }
        val newRole = existingRole.copy(roleName = updatedRole.roleName)
        return roleRepository.save(newRole)
    }

    // Удалить роль
    fun deleteRole(id: Long) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Role with id $id not found")
        }
    }
}



@RestController
@RequestMapping("roles")
class RoleController(private val roleService: RoleService) {

    // Получить все роли
    @GetMapping
    fun getAllRoles(): ResponseEntity<List<Role>> {
        val roles = roleService.getAllRoles()
        return ResponseEntity.ok(roles)
    }

    // Найти роль по ID
    @GetMapping("/{id}")
    fun getRoleById(@PathVariable id: Long): ResponseEntity<Role> {
        val role = roleService.getRoleById(id)
        return role.map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    // Создать новую роль
    @PostMapping
    fun createRole(@RequestBody role: Role): ResponseEntity<Role> {
        val newRole = roleService.createRole(role)
        return ResponseEntity.ok(newRole)
    }

    // Обновить существующую роль
    @PutMapping("/{id}")
    fun updateRole(
        @PathVariable id: Long,
        @RequestBody updatedRole: Role
    ): ResponseEntity<Role> {
        return try {
            val role = roleService.updateRole(id, updatedRole)
            ResponseEntity.ok(role)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    // Удалить роль
    @DeleteMapping("/{id}")
    fun deleteRole(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            roleService.deleteRole(id)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}
