package com.example.demo.service

import com.example.demo.model.Team
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.TeamRequest
import com.example.demo.payload.response.PagedResponse

interface TeamService {
    fun getAllTeams(page: Int, size: Int): PagedResponse<Team>

    fun addTeam(teamRequest: TeamRequest, currentUser: UserPrincipal)
}