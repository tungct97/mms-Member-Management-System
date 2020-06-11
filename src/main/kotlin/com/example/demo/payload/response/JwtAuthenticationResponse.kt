package com.example.demo.payload.response

data class JwtAuthenticationResponse(val accessToken: String? = null, val tokenType: String = "Bearer")