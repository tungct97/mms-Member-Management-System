package com.example.demo.controller

import com.example.demo.model.CurrentUser
import com.example.demo.model.Position
import com.example.demo.model.UserPrincipal
import com.example.demo.payload.response.ApiResponse
import com.example.demo.payload.response.PagedResponse
import com.example.demo.service.PositionService
import com.example.demo.utils.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/positions")
class PositionController {
    @Autowired
    private lateinit var positionService: PositionService

    @GetMapping
    fun getAllPositions(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
                        @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<Position> {
        return positionService.getAllPositions(page, size)
    }

    @PostMapping
    fun addPosition(@RequestBody position: Position, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Position> {
        return positionService.addPosition(position, currentUser)
    }

    @PutMapping("/{id}")
    fun updatePosition(@PathVariable(name = "id") id: Long, @RequestBody position: Position, @CurrentUser currentUser: UserPrincipal): ResponseEntity<Position> {
        return positionService.updatePosition(id, position, currentUser)
    }

    @GetMapping("/{id}")
    fun getPosition(@PathVariable(name = "id") id: Long): ResponseEntity<Position> {
        return positionService.getPosition(id)
    }

    @DeleteMapping("/{id}")
    fun deletePosition(@PathVariable(name = "id") id: Long, @CurrentUser currentUser: UserPrincipal): ResponseEntity<ApiResponse> {
        return positionService.deletePosition(id, currentUser)
    }
}