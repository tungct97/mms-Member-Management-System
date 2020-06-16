package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Project
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.ProjectRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.payload.response.ProjectResponse
import com.example.demo.repository.ProjectRepository
import com.example.demo.repository.TeamRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.ProjectService
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectServiceImpl : ProjectService {
    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var teamRepository: TeamRepository

    override fun getAllProjects(page: Int, size: Int): PagedResponse<Project> {
        AppUtils.validatePageNumberAndSize(page, size)
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        val projects = projectRepository.findAll(pageable)

        val content = if (projects.numberOfElements == 0) Collections.emptyList() else projects.content

        return PagedResponse(content, projects.number, projects.size, projects.totalElements, projects.totalPages, projects.isLast)
    }

    override fun addProject(projectRequest: ProjectRequest, currentUser: UserPrincipal): ResponseEntity<Project> {
        val leader = userRepository.findById(projectRequest.leader).orElseThrow { ResourceNotFoundException("User", "id", projectRequest.leader) }
        val team = teamRepository.findById(projectRequest.team).orElseThrow { ResourceNotFoundException("Team", "id", projectRequest.team) }
        val project = Project()
        project.leader = leader
        project.abbreviation = projectRequest.abbreviation
        project.endDate = projectRequest.endDate
        project.startDate = projectRequest.startDate
        project.name = projectRequest.name
        project.team = team
        return ResponseEntity(projectRepository.save(project), HttpStatus.CREATED)
    }

    override fun updateProject(id: Long, projectRequest: ProjectRequest, currentUser: UserPrincipal): ResponseEntity<Project> {
        val leader = userRepository.findById(projectRequest.leader).orElseThrow { ResourceNotFoundException("User", "id", projectRequest.leader) }
        val team = teamRepository.findById(projectRequest.team).orElseThrow { ResourceNotFoundException("Team", "id", projectRequest.team) }
        val project = projectRepository.findById(id).orElseThrow { ResourceNotFoundException("Project", "id", id) }
        project.leader = leader
        project.abbreviation = projectRequest.abbreviation
        project.endDate = projectRequest.endDate
        project.startDate = projectRequest.startDate
        project.name = projectRequest.name
        project.team = team
        return ResponseEntity(projectRepository.save(project), HttpStatus.OK)
    }

    override fun deleteProject(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        projectRepository.deleteById(id)
        return ResponseEntity(ApiResponse(true, "You successfully deleted project"), HttpStatus.OK)
    }

    override fun getProject(id: Long): ResponseEntity<Project> {
        val project = projectRepository.findById(id).orElseThrow { ResourceNotFoundException("Project", "id", id) }
        return ResponseEntity(project, HttpStatus.OK)
    }
}