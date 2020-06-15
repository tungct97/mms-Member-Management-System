package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Team
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.TeamRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.TeamRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.TeamService
import com.example.demo.utils.AppConstants
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeamServiceImpl : TeamService {
    @Autowired
    private lateinit var teamRepository: TeamRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun getAllTeams(page: Int, size: Int): PagedResponse<Team> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        val teams = teamRepository.findAll(pageable)

        val content = if (teams.numberOfElements == 0) Collections.emptyList() else teams.content

        return PagedResponse(content, teams.number, teams.size, teams.totalElements, teams.totalPages, teams.isLast)
    }

    override fun addTeam(teamRequest: TeamRequest, currentUser: UserPrincipal): ResponseEntity<Team> {
        val user = userRepository.findById(teamRequest.leader).orElseThrow { ResourceNotFoundException("User", AppConstants.ID, 1L) }

        val members = mutableListOf<User>()
        teamRequest.members?.forEach {
            val newMember = userRepository.findById(it).orElseThrow { ResourceNotFoundException("User", AppConstants.ID, 1L) }
            members.add(newMember)
        }

        val newTeam = Team()
        newTeam.leader = user
        newTeam.description = teamRequest.description
        newTeam.name = teamRequest.name
        newTeam.teamMembers = members
        val team = teamRepository.save(newTeam)
        return ResponseEntity(team, HttpStatus.CREATED)
    }

    override fun getTeam(id: Long): ResponseEntity<Team> {
        val team = teamRepository.findById(id).orElseThrow { ResourceNotFoundException("Team", AppConstants.ID, 1L) }

        return ResponseEntity(team, HttpStatus.OK)
    }

    override fun updateTeam(id: Long, teamRequest: TeamRequest, currentUser: UserPrincipal): ResponseEntity<Team> {
        val user = userRepository.findById(teamRequest.leader).orElseThrow { ResourceNotFoundException("User", AppConstants.ID, 1L) }
        val team = teamRepository.findById(id).orElseThrow { ResourceNotFoundException("User", AppConstants.ID, 1L) }

        val members = mutableListOf<User>()
        teamRequest.members?.forEach {
            val newMember = userRepository.findById(it).orElseThrow { ResourceNotFoundException("User", AppConstants.ID, 1L) }
            members.add(newMember)
        }

        team.leader = user
        team.description = teamRequest.description
        team.name = teamRequest.name
        team.teamMembers = members

        return ResponseEntity(teamRepository.save(team), HttpStatus.OK)
    }

    override fun deleteTeam(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        teamRepository.deleteById(id)
        return ResponseEntity(ApiResponse(true, "You successfully deleted team"), HttpStatus.OK)
    }
}