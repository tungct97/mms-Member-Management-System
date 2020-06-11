package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.MemberSkillRequest
import com.example.demo.service.MemberSkillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/memberskills")
class MemberSkillController {
    @Autowired
    private lateinit var memberSkillService: MemberSkillService

    @PostMapping("/{id}")
    fun addMemberSkill(@PathVariable(name = "id") id: Long,
                       @RequestBody memberSkillsRequest: List<MemberSkillRequest>,
                       @CurrentUser currentUser: UserPrincipal): ResponseEntity<User> {
        val memberSkillResponse = memberSkillService.addMemberSkill(id, memberSkillsRequest, currentUser)
        return ResponseEntity(memberSkillResponse, HttpStatus.CREATED)
    }
}