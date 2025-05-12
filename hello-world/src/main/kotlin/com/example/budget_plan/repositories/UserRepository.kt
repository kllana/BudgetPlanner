package com.example.budget_plan.repositories

import com.example.budget_plan.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName: String): Optional<User>


}



