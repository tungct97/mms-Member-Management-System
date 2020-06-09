package com.example.demo.controller

import com.example.demo.exception.AppException
import com.example.demo.model.User
import com.example.demo.model.role.Role
import com.example.demo.model.role.RoleName
import com.example.demo.payload.ApiResponse
import com.example.demo.payload.JwtAuthenticationResponse
import com.example.demo.payload.LoginRequest
import com.example.demo.payload.SignUpRequest
import com.example.demo.repository.RoleRepository
import com.example.demo.repository.UserRepository
import com.example.demo.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    var tokenProvider: JwtTokenProvider? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.usernameOrEmail,
                        loginRequest.password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider!!.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity<Any?>(ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity<Any?>(ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)
        }

        // Creating user's account
        val user = User(name = signUpRequest.name, username = signUpRequest.username,
                email = signUpRequest.email, password = signUpRequest.password)
        user.password = (passwordEncoder.encode(user.password))
        val roles = mutableListOf<Role?>()
        if (userRepository.count().toInt() == 0) {
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)?.orElseThrow { AppException("User Role not set.") })
            roles.add(roleRepository.findByName(RoleName.ROLE_USER)?.orElseThrow { AppException("User Role not set.") })
        } else {
            roles.add(roleRepository.findByName(RoleName.ROLE_USER)?.orElseThrow { AppException("User Role not set.") })
        }

        user.roles = roles

        val result: User = userRepository.save(user)
        val location: URI = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.username).toUri()
        return ResponseEntity.created(location).body(ApiResponse(true, "User registered successfully"))
    }
}