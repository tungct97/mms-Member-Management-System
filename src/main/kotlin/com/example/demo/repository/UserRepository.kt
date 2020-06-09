package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String?): Optional<User?>?

    fun findByEmail(email: String?): Optional<User?>?

    fun existsByUsername(username: String?): Boolean

    fun existsByEmail(email: String?): Boolean

    fun findByUsernameOrEmail(username: String?, email: String?): Optional<User?>?
}