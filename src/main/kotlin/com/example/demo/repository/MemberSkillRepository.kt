package com.example.demo.repository

import com.example.demo.model.MemberSkill
import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
@Transactional
interface MemberSkillRepository : JpaRepository<MemberSkill, Long> {
    fun findByUserId(userId: Long): MutableList<MemberSkill>?

    fun deleteMemberSkillsByUser(user: User?)
}