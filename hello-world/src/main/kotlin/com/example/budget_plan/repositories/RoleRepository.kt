

package com.example.budget_plan.repositories

import com.example.budget_plan.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    //fun findByNameOfRole(nameOfRole: String): Role?
    fun findByRoleName(roleName: String): Role?
}


