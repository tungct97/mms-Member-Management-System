package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.PagedResponse

interface UserService {
    fun getAllUsers(page: Int, size: Int): PagedResponse<User>

    fun getCurrentUser(currentUser: UserPrincipal): User
}