package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.Team
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.TeamRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.service.TeamService
import com.example.demo.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/teams")
class TeamController {
    @Autowired
    private lateinit var teamService: TeamService

    @GetMapping
    fun getAllTeams(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
                    @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int): PagedResponse<Team> {
        return teamService.getAllTeams(page, size)
    }

    @PostMapping
    fun addTeam(@RequestBody newTeam: TeamRequest, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Team> {
        return teamService.addTeam(newTeam, currentUser)
    }

    @GetMapping("/{id}")
    fun getTeam(@PathVariable(name = "id") id: Long, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Team> {
        return teamService.getTeam(id)
    }

    @PutMapping("/{id}")
    fun updateTeam(@PathVariable(name = "id") id: Long, @RequestBody newTeam: TeamRequest, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Team> {
        return teamService.updateTeam(id, newTeam, currentUser)
    }

    @DeleteMapping("/{id}")
    fun deleteTeam(@PathVariable(name = "id") id: Long, @CurrentUser currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        return teamService.deleteTeam(id, currentUser)
    }
}
