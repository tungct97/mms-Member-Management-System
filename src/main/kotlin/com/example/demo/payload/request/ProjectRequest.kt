package com.example.demo.payload.request

data class ProjectRequest(
        val name: String? = null,
        var leader: Long? = null,
        val team: Long? = null,
        var abbreviation: String? = null,
        var startDate: String? = null,
        var endDate: String? = null
)