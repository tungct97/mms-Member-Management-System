package com.example.demo.exception

import org.springframework.http.HttpStatus

class DemoException : RuntimeException {
    val status: HttpStatus
    override val message: String

    constructor(status: HttpStatus, message: String) : super() {
        this.status = status
        this.message = message
    }

    constructor(status: HttpStatus, message: String, exception: Throwable?) : super(exception) {
        this.status = status
        this.message = message
    }

    companion object {
        private const val serialVersionUID = -6593330219878485669L
    }
}
