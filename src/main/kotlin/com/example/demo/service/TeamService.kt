package com.example.demo.service

import com.example.demo.model.Team
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.TeamRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import org.springframework.http.ResponseEntity

interface TeamService {
    fun getAllTeams(page: Int, size: Int): PagedResponse<Team>

    fun addTeam(teamRequest: TeamRequest, currentUser: UserPrincipal): ResponseEntity<Team>

    fun getTeam(id: Long): ResponseEntity<Team>

    fun updateTeam(id: Long, teamRequest: TeamRequest, currentUser: UserPrincipal): ResponseEntity<Team>

    fun deleteTeam(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse>
}