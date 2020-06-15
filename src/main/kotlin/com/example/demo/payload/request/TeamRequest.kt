package com.example.demo.payload.request

data class TeamRequest(val id: Long? = null, val name: String? = null, val description: String? = null, val leader: Long? = null, val members: MutableList<Long>? = null)