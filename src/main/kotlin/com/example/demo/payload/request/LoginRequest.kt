package com.example.demo.payload.request

data class LoginRequest(
        val usernameOrEmail: String? = null,
        val password: String? = null
)