package com.example.springwebflux.controller

import com.example.springwebflux.service.ExampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleReactiveController(private val exampleService: ExampleService) {
    @GetMapping("/hello/{throw}")
    suspend fun execute(@PathVariable("throw") throwException: Boolean): String {
        val todo = exampleService.createTwice(throwException)
        return "hello: $todo"
    }

    @GetMapping("/hello-no-tx/{throw}")
    suspend fun executeWithoutTx(@PathVariable("throw") throwException: Boolean): String {
        exampleService.createWithoutTransaction(throwException)
        return "hello"
    }

    @GetMapping("/hello-manual-tx/{throw}")
    suspend fun executeWithManualTx(@PathVariable("throw") throwException: Boolean): String {
        exampleService.createWithManualTransaction(throwException)
        return "hello"
    }
}
