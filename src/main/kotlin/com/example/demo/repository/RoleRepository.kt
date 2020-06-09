package com.example.demo.repository

import com.example.demo.model.role.Role
import com.example.demo.model.role.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: RoleName?): Optional<Role?>?
}
