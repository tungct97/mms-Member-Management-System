package com.example.demo.payload.response

import org.springframework.security.core.userdetails.User

data class ProjectResponse(
        val id: Long? = null,
        val name: String? = null,
        val lead: User? = null,
        val team: Int? = null,
        val abbreviation: String? = null,
        val startDate: String? = null,
        val endDate: String? = null
)