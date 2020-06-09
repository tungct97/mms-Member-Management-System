package com.example.demo.service

import com.example.demo.model.UserPrincipal
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional
@Service
open class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository


    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = userRepository.findByUsernameOrEmail(username, username)?.orElseThrow { UsernameNotFoundException("User not found with username or email : $username") }
        return user?.let { UserPrincipal.create(it) }
    }

    @Transactional
    open fun loadUserById(id: Long): UserPrincipal? {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found with id : $id") }
        return user?.let { UserPrincipal.create(user) }
    }
}