package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.Skill
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.service.SkillService
import com.example.demo.utils.AppConstants.DEFAULT_PAGE_NUMBER
import com.example.demo.utils.AppConstants.DEFAULT_PAGE_SIZE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/skills")
class SkillsController {

    @Autowired
    private lateinit var skillsService: SkillService

    @GetMapping
    fun getAllSkills(@RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) page: Int,
                     @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) size: Int): PagedResponse<Skill> {
        return skillsService.getAllSkills(page, size)
    }

    @PostMapping
    fun addSkill(@RequestBody skill: Skill, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Skill> {
        return skillsService.addSkill(skill, currentUser)
    }

    @GetMapping("/{id}")
    fun getSkill(@PathVariable(name = "id") id: Long): ResponseEntity<Skill> {
        return skillsService.getSkill(id)
    }

    @PutMapping("/{id}")
    fun updateSkill(@PathVariable(name = "id") id: Long, @RequestBody skill: Skill, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Skill> {
        return skillsService.updateSkill(id, skill, currentUser)
    }

    @DeleteMapping("/{id}")
    fun deleteSkill(@PathVariable(name = "id") id: Long, @CurrentUser currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        return skillsService.deleteSkill(id, currentUser)
    }
}