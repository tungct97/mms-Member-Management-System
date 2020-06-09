package com.example.demo.payload

data class JwtAuthenticationResponse(val accessToken: String? = null, val tokenType: String = "Bearer")