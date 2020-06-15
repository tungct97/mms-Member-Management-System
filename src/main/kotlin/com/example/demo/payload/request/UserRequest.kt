package com.example.demo.payload.request

data class UserRequest(
        val name: String? = null,
        val username: String? = null,
        val email: String? = null,
        val phone: String? = null,
        val memberSkills: List<MemberSkillRequest>? = null,
        val position: Long? = null
)
