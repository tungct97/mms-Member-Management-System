package com.example.demo.payload

data class LoginRequest(
        val usernameOrEmail: String? = null,
        val password: String? = null
)