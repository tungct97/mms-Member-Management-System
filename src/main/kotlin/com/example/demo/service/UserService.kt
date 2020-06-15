package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.request.UserRequest
import com.example.demo.payload.response.PagedResponse
import org.springframework.http.ResponseEntity

interface UserService {
    fun getAllUsers(page: Int, size: Int): PagedResponse<User>

    fun getCurrentUser(currentUser: UserPrincipal): User

    fun updateUser(id: Long, newUser: UserRequest, currentUser: UserPrincipal): ResponseEntity<User>
}