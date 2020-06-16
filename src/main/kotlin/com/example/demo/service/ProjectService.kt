package com.example.demo.service

import com.example.demo.model.Project
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.ProjectRequest
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import org.springframework.http.ResponseEntity

interface ProjectService {
    fun getAllProjects(page: Int, size: Int): PagedResponse<Project>

    fun addProject(projectRequest: ProjectRequest, currentUser: UserPrincipal): ResponseEntity<Project>

    fun updateProject(id: Long, projectRequest: ProjectRequest, currentUser: UserPrincipal): ResponseEntity<Project>

    fun deleteProject(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse>

    fun getProject(id: Long): ResponseEntity<Project>
}