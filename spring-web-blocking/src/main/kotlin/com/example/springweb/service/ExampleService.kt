package com.example.springweb.service

import com.example.springweb.model.Todo
import com.example.springweb.model.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ExampleService(private val todoRepository: TodoRepository) {
    @Transactional
    fun create(throwException: Boolean): Todo {
        val todo = todoRepository.create(UUID.randomUUID().toString(), false)
        if (throwException) {
            throw IllegalStateException("Something wrong.")
        }
        return todoRepository.create(UUID.randomUUID().toString(), false)
    }
}

