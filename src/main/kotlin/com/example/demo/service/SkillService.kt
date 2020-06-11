package com.example.demo.service

import com.example.demo.exception.UnauthorizedException
import com.example.demo.model.Skill
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import org.springframework.http.ResponseEntity

interface SkillService {
    fun getAllSkills(page: Int, size: Int): PagedResponse<Skill>

    fun addSkill(skill: Skill, currentUser: UserPrincipal): ResponseEntity<Skill>

    fun getSkill(id: Long): ResponseEntity<Skill>

    @Throws(UnauthorizedException::class)
    fun updateSkill(id: Long, newSkill: Skill, currentUser: UserPrincipal): ResponseEntity<Skill>

    @Throws(UnauthorizedException::class)
    fun deleteSkill(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse>
}