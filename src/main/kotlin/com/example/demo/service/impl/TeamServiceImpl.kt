package com.example.demo.service.impl

import com.example.demo.model.Team
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.TeamRequest
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.TeamRepository
import com.example.demo.service.TeamService
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeamServiceImpl : TeamService {
    @Autowired
    private lateinit var teamRepository: TeamRepository

    override fun getAllTeams(page: Int, size: Int): PagedResponse<Team> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        val teams = teamRepository.findAll(pageable)

        val content = if (teams.numberOfElements == 0) Collections.emptyList() else teams.content

        return PagedResponse(content, teams.number, teams.size, teams.totalElements, teams.totalPages, teams.isLast)
    }

    override fun addTeam(teamRequest: TeamRequest, currentUser: UserPrincipal) {
        TODO("Not yet implemented")
    }
}