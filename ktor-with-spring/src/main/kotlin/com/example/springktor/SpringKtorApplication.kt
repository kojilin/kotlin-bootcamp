package com.example.springktor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKtorApplication

fun main(vararg args: String) {
    runApplication<SpringKtorApplication>(*args)
}
