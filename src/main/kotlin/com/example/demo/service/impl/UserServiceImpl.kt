package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.UserRepository
import com.example.demo.service.UserService
import com.example.demo.utils.AppConstants
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun getAllUsers(page: Int, size: Int): PagedResponse<User> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size)

        val users = userRepository.findAll(pageable)

        return PagedResponse(users.content, users.number, users.size, users.totalElements, users.totalPages, users.isLast)
    }

    override fun getCurrentUser(currentUser: UserPrincipal): User {
        return userRepository.findById(currentUser.id).orElseThrow { ResourceNotFoundException(AppConstants.USER, AppConstants.ID, 1L) }
    }
}