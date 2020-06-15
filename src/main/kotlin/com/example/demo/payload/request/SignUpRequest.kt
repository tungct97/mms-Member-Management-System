package com.example.demo.payload.request

data class SignUpRequest(val name: String? = null, val username: String? = null, val email: String? = null, val password: String? = null, val phone: String? = null)