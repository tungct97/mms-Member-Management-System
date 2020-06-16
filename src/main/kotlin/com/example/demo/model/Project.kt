package com.example.demo.model

import com.example.demo.model.audit.UserDateAudit
import javax.persistence.*

@Entity
data class Project(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var leader: User? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id")
        var team: Team? = null,
        var name: String? = null,
        var abbreviation: String? = null,
        var startDate: String? = null,
        var endDate: String? = null
) : UserDateAudit()