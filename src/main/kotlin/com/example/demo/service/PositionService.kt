package com.example.demo.service

import com.example.demo.model.Position
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import org.springframework.http.ResponseEntity

interface PositionService {
    fun getAllPositions(page: Int, size: Int): PagedResponse<Position>

    fun addPosition(position: Position, currentUser: UserPrincipal): ResponseEntity<Position>

    fun getPosition(id: Long): ResponseEntity<Position>

    fun updatePosition(id: Long, newPosition: Position, currentUser: UserPrincipal): ResponseEntity<Position>

    fun deletePosition(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse>
}