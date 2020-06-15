package com.example.demo.model

import com.example.demo.model.audit.UserDateAudit
import javax.persistence.*

@Entity
data class Team(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        var leader: User? = null,
        var name: String? = null,
        var description: String? = null,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "team_member", joinColumns = [JoinColumn(name = "team_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")])
        var teamMembers: MutableList<User>? = null
) : UserDateAudit()