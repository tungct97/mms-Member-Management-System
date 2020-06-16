package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.Project
import com.example.demo.model.Skill
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.ProjectRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.service.ProjectService
import com.example.demo.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
class ProjectController {
    @Autowired
    private lateinit var projectService: ProjectService

    @GetMapping
    fun getProjects(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
                    @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int): PagedResponse<Project> {
        return projectService.getAllProjects(page, size)
    }

    @PostMapping
    fun addProject(@RequestBody projectRequest: ProjectRequest, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Project> {
        return projectService.addProject(projectRequest, currentUser)
    }

    @PutMapping("/{id}")
    fun updateProject(@PathVariable(name = "id") id: Long, @RequestBody projectRequest: ProjectRequest, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Project> {
        return projectService.updateProject(id, projectRequest, currentUser)
    }

    @DeleteMapping("/{id}")
    fun deleteProject(@PathVariable(name = "id") id: Long, @CurrentUser currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        return projectService.deleteProject(id, currentUser)
    }

    @GetMapping("/{id}")
    fun getSkill(@PathVariable(name = "id") id: Long): ResponseEntity<Project> {
        return projectService.getProject(id)
    }
}