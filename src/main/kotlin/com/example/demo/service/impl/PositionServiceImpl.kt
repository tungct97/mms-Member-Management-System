package com.example.demo.service.impl

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Position
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.repository.PositionRepository
import com.example.demo.service.PositionService
import com.example.demo.utils.AppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class PositionServiceImpl : PositionService {
    @Autowired
    private lateinit var positionRepository: PositionRepository

    override fun getAllPositions(page: Int, size: Int): PagedResponse<Position> {
        AppUtils.validatePageNumberAndSize(page, size)

        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        val positions = positionRepository.findAll(pageable)

        val content = if (positions.numberOfElements == 0) Collections.emptyList() else positions.content

        return PagedResponse(content, positions.number, positions.size, positions.totalElements, positions.totalPages, positions.isLast)
    }

    override fun addPosition(position: Position, currentUser: UserPrincipal): ResponseEntity<Position> {
        val position = positionRepository.save(position)
        return ResponseEntity(position, HttpStatus.CREATED)
    }

    override fun getPosition(id: Long): ResponseEntity<Position> {
        val position = positionRepository.findById(id).orElseThrow { ResourceNotFoundException("Category", "id", id) }
        return ResponseEntity(position, HttpStatus.OK)
    }

    override fun updatePosition(id: Long, newPosition: Position, currentUser: UserPrincipal): ResponseEntity<Position> {
        val position = positionRepository.findById(id).orElseThrow { ResourceNotFoundException("Category", "id", id) }
        position.name = newPosition.name
        return ResponseEntity(position, HttpStatus.OK)
    }

    override fun deletePosition(id: Long, currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        positionRepository.deleteById(id)
        return ResponseEntity(ApiResponse(true, "You successfully deleted position"), HttpStatus.OK)
    }
}