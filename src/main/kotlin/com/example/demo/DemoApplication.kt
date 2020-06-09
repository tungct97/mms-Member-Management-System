package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
open class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
