package com.example.demo.model

import com.example.demo.model.role.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors


class UserPrincipal(
        val id: Long?,
        val name: String,
        private val username: String,
        @field:JsonIgnore val email: String?,
        @field:JsonIgnore private val password: String,
        @field:JsonIgnore val roles: List<Role?>,
        private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserPrincipal
        return Objects.equals(id, that.id)
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities: MutableList<SimpleGrantedAuthority>? = user.roles?.stream()?.map { role -> SimpleGrantedAuthority(role?.name?.name) }?.collect(Collectors.toList())
            return UserPrincipal(
                    user.id,
                    user.name ?: "",
                    user.username ?: "",
                    user.email ?: "",
                    user.password ?: "",
                    user.roles ?: listOf(),
                    authorities ?: mutableListOf()
            )
        }
    }
}