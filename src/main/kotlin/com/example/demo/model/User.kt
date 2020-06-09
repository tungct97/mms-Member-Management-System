package com.example.demo.model

import com.example.demo.model.audit.DateAudit
import com.example.demo.model.role.Role
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
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
        var roles: List<Role?>? = null
) {

    companion object {
        private const val serialVersionUID = 1L
    }
}
