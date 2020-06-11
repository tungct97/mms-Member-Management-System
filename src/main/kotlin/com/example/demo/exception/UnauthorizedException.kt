package com.example.demo.exception

import com.example.demo.payload.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
data class UnauthorizedException(var apiResponse: ApiResponse? = null, override var message: String? = null) : RuntimeException() {
    companion object {
        private const val serialVersionUID = 1L
    }
}
