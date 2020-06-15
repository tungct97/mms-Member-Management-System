package com.example.demo.model

import com.example.demo.model.audit.UserDateAudit
import javax.persistence.*

@Entity
data class Team(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        val leader: User? = null,
        val name: String? = null,
        val description: String? = null
) : UserDateAudit()