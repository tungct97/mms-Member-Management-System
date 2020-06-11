package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.MemberSkill
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.MemberSkillRequest
import com.example.demo.repository.MemberSkillRepository
import com.example.demo.repository.SkillsRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.MemberSkillService
import com.example.demo.utils.AppConstants.ID
import com.example.demo.utils.AppConstants.SKILL
import com.example.demo.utils.AppConstants.USER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MemberSkillServiceImpl : MemberSkillService {
    @Autowired
    private lateinit var memberSkillRepository: MemberSkillRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var skillRepository: SkillsRepository

    override fun addMemberSkill(id: Long, memberSkillsRequest: List<MemberSkillRequest>, currentUser: UserPrincipal): User {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException(USER, ID, 1L) }

        val listMemberSkill = user.memberSkills

        memberSkillsRequest.forEach {
            val memberSkill = MemberSkill()
            memberSkill.level = it.level
            memberSkill.user = user
            memberSkill.yearUsed = it.yearUsed
            memberSkill.skill = skillRepository.findById(it.skillId).orElseThrow { ResourceNotFoundException(SKILL, ID, 1L) }
            listMemberSkill?.add(memberSkill)
        }

        memberSkillRepository.saveAll(listMemberSkill)

        user.memberSkills = listMemberSkill

        userRepository.save(user)

        return user
    }

}