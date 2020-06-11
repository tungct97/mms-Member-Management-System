package com.example.demo.model

import com.example.demo.model.audit.UserDateAudit
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Skill(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
        var name: String? = null,
        @OneToMany(mappedBy = "skill", cascade = [CascadeType.ALL], orphanRemoval = true)
        @JsonIgnore
        val memberSkill: List<MemberSkill>? = null
) : UserDateAudit()