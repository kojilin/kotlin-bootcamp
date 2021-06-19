package com.example.springwebflux.service

import com.example.springwebflux.model.Todo
import com.example.springwebflux.model.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import java.time.Clock
import java.time.Instant
import java.util.*

@Service
class ExampleService(
    private val todoRepository: TodoRepository,
    private val operator: TransactionalOperator,
    private val clock: Clock
) {
    @Transactional
    suspend fun createTwice(throwException: Boolean): Todo {
        todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
        if (throwException) {
            throw IllegalStateException("Something wrong.")
        }
        val entity = Todo(null, UUID.randomUUID().toString(), Instant.now(clock))
        todoRepository.save(entity)
        return entity.id?.let {
            return@let todoRepository.findById(it)
        } ?: throw java.lang.IllegalStateException("No inserted data")
    }

    suspend fun createWithoutTransaction(throwException: Boolean) {
        todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
        if (throwException) {
            throw IllegalStateException("Something wrong.")
        }
        todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
    }

    suspend fun createWithManualTransaction(throwException: Boolean) {
        operator.executeAndAwait {
            todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
            if (throwException) {
                throw IllegalStateException("Something wrong.")
            }
            todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
        }
    }
}
