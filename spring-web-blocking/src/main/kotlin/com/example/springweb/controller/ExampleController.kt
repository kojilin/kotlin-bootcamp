package com.example.springweb.controller

import com.example.springweb.service.ExampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(private val exampleService: ExampleService) {
    @GetMapping("/hello/{throw}")
    fun execute(@PathVariable("throw") throwException: Boolean): String {
        val todo = exampleService.create(throwException)
        return "hello: $todo"
    }
}
