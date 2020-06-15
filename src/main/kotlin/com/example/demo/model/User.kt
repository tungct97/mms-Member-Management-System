package com.example.demo.model

import com.example.demo.model.audit.DateAudit
import com.example.demo.model.role.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.NaturalId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"]), UniqueConstraint(columnNames = ["email"])])
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null,
        var username: String? = null,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var password: String? = null,
        @NaturalId
        var email: String? = null,
        var phone: String? = null,
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        var roles: List<Role?>? = null,
        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        var memberSkills: MutableList<MemberSkill>? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        var position: Position? = null,
        @OneToMany(mappedBy = "leader", cascade = [CascadeType.ALL], orphanRemoval = true)
        @JsonIgnore
        var teamsLead: MutableList<Team>? = null
) : DateAudit() {

    companion object {
        private const val serialVersionUID = 1L
    }
}
