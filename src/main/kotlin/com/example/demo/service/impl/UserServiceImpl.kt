package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.MemberSkill
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.UserRequest
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.MemberSkillRepository
import com.example.demo.repository.PositionRepository
import com.example.demo.repository.SkillsRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.UserService
import com.example.demo.utils.AppConstants
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var positionRepository: PositionRepository

    @Autowired
    private lateinit var memberSkillsRepository: MemberSkillRepository

    @Autowired
    lateinit var skillRepository: SkillsRepository

    override fun getAllUsers(page: Int, size: Int): PagedResponse<User> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size)

        val users = userRepository.findAll(pageable)

        return PagedResponse(users.content, users.number, users.size, users.totalElements, users.totalPages, users.isLast)
    }

    override fun getCurrentUser(currentUser: UserPrincipal): User {
        return userRepository.findById(currentUser.id).orElseThrow { ResourceNotFoundException(AppConstants.USER, AppConstants.ID, 1L) }
    }

    override fun updateUser(id: Long, newUser: UserRequest, currentUser: UserPrincipal): ResponseEntity<User> {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException(AppConstants.USER, AppConstants.ID, 1L) }
        val position = positionRepository.findById(newUser.position).orElseThrow { ResourceNotFoundException("Position", AppConstants.ID, 1L) }

        val listMemberSkills = mutableListOf<MemberSkill>()

        newUser.memberSkills?.forEach {
            val memberSkill = MemberSkill()
            memberSkill.user = user
            memberSkill.yearUsed = it.yearUsed
        }

        newUser.memberSkills?.forEach {
            val memberSkill = MemberSkill()
            memberSkill.level = it.level
            memberSkill.yearUsed = it.yearUsed
            memberSkill.user = user
            memberSkill.skill = skillRepository.findById(it.skillId).orElseThrow { ResourceNotFoundException("Skill", AppConstants.ID, 1L) }
            listMemberSkills.add(memberSkill)
        }

        memberSkillsRepository.deleteMemberSkillsByUser(user)

        user.email = newUser.email ?: user.email
        user.username = newUser.username ?: user.username
        user.name = newUser.name ?: user.name
        user.phone = newUser.phone ?: user.phone
        user.position = position ?: user.position
        user.memberSkills = if (newUser.memberSkills == null) user.memberSkills else listMemberSkills

        val newUser = userRepository.save(user)

        newUser.memberSkills = memberSkillsRepository.findByUserId(id)

        return ResponseEntity(newUser, HttpStatus.OK)
    }
}