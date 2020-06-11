package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.MemberSkillRequest
import org.springframework.http.ResponseEntity

interface MemberSkillService {
    fun addMemberSkill(id: Long, memberSkillsRequest: List<MemberSkillRequest>, currentUser: UserPrincipal): User
}