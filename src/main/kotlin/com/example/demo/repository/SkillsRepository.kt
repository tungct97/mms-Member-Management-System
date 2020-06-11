package com.example.demo.repository

import com.example.demo.model.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillsRepository : JpaRepository<Skill, Long>