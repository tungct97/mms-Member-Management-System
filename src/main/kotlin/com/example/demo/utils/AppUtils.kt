package com.example.demo.utils

import com.example.demo.exception.DemoException
import org.springframework.http.HttpStatus

object AppUtils {
    fun validatePageNumberAndSize(page: Int, size: Int) {
        if (page < 0) {
            throw DemoException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.")
        }
        if (size < 0) {
            throw DemoException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.")
        }
        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw DemoException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE)
        }
    }
}
