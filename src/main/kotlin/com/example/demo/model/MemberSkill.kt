package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class MemberSkill(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        val id: Long? = null,
        var level: String? = null,
        var yearUsed: Int? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "skill_id")
        var skill: Skill? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        @JsonIgnore
        var user: User? = null
)