package com.example.demo.payload.response

data class PagedResponse<T>(
        val data: List<T>? = listOf(),
        val page: Int? = null,
        val size: Int? = null,
        val totalElement: Long? = null,
        val totalPages: Int? = null,
        val last: Boolean? = null
)
