package com.example.demo.repository

import com.example.demo.model.MemberSkill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberSkillRepository : JpaRepository<MemberSkill, Long>