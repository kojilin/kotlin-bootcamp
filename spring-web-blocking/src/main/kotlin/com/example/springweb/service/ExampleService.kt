package com.example.springweb.service

import com.example.springweb.repository.Todo
import com.example.springweb.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.Instant
import java.util.*

@Service
class ExampleService(private val todoRepository: TodoRepository, private val clock: Clock) {
    @Transactional
    fun create(throwException: Boolean): Todo {
        val todo = Todo(null, UUID.randomUUID().toString(), Instant.now(clock))
        todoRepository.insert(todo)
        if (throwException) {
            throw IllegalStateException("Something wrong.")
        }
        return todo.id?.let {
            return@let todoRepository.find(it)
        } ?: throw java.lang.IllegalStateException("No inserted data")
    }
}
