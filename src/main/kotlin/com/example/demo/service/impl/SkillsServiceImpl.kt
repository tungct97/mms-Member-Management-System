package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Skill
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.SkillsRepository
import com.example.demo.service.SkillService
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SkillsServiceImpl : SkillService {
    @Autowired
    private lateinit var skillsRepository: SkillsRepository

    override fun getAllSkills(page: Int, size: Int): PagedResponse<Skill> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        val skills = skillsRepository.findAll(pageable)

        val content = if (skills.numberOfElements == 0) emptyList<Skill>() else skills.content

        return PagedResponse(content, skills.number, skills.size, skills.totalElements, skills.totalPages, skills.isLast)
    }

    override fun addSkill(skill: Skill, currentUser: UserPrincipal): ResponseEntity<Skill> {
        val skill = skillsRepository.save(skill)
        return ResponseEntity(skill, HttpStatus.CREATED)
    }

    override fun getSkill(id: Long): ResponseEntity<Skill> {
        val skill = skillsRepository.findById(id).orElseThrow { ResourceNotFoundException("Skill", "id", id) }
        return ResponseEntity(skill, HttpStatus.OK)
    }

    override fun updateSkill(id: Long, newSkill: Skill, currentUser: UserPrincipal): ResponseEntity<Skill> {
        val skill = skillsRepository.findById(id).orElseThrow { ResourceNotFoundException("Skill", "id", id) }
        skill.name = newSkill.name
        return ResponseEntity(skillsRepository.save(skill), HttpStatus.OK)
    }

    override fun deleteSkill(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        skillsRepository.deleteById(id)
        return ResponseEntity(ApiResponse(true, "You successfully deleted skill"), HttpStatus.OK)
    }
}