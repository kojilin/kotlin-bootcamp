package com.example.springktor.service

import com.example.springktor.client.RandomClient
import com.example.springktor.model.Todo
import com.example.springktor.model.TodoRepository
import com.example.springktor.service.ExampleService.ExampleService.REQUEST_NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Service
class ExampleService(
    private val todoRepository: TodoRepository,
    private val operator: TransactionalOperator,
    private val client: RandomClient,
    private val clock: Clock
) {
    @Transactional
    suspend fun createTwice(throwException: Boolean): Todo {
        todoRepository.save(Todo(null, UUID.randomUUID().toString(), Instant.now(clock)))
        val response = client.getRandom(REQUEST_NUMBER).await()
        val body = withContext(Dispatchers.IO) {
            @Suppress("BlockingMethodInNonBlockingContext")
            response.string()
        }
        val entity = Todo(null, body, Instant.now(clock))
        todoRepository.save(entity)

        if (throwException) {
            throw IllegalStateException("Something wrong.")
        }

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

    object ExampleService {
        const val REQUEST_NUMBER = 12
    }
}
