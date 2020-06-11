package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.User
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.PagedResponse
import com.example.demo.service.UserService
import com.example.demo.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun getAllUsers(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
                    @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int): PagedResponse<User> {
        return userService.getAllUsers(page, size)
    }


    //Đang lỗi
    @GetMapping("/me")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): ResponseEntity<User>? {
        val userSummary = userService.getCurrentUser(currentUser)
        return ResponseEntity(userSummary, HttpStatus.OK)
    }
}