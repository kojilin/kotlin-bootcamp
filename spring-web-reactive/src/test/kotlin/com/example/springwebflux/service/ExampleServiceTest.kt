package com.example.springwebflux.service

import com.example.springwebflux.config.TestConfig
import com.example.springwebflux.model.AbstractContainerBaseTest
import com.example.springwebflux.model.TodoRepository
import com.example.springwebflux.runBlockingAndRollback
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.reactive.TransactionalOperator

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    classes = [TestConfig::class],
    properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
class ExampleServiceTest(
    @Autowired private val service: ExampleService,
    @Autowired private val operator: TransactionalOperator,
    @Autowired private val todoRepository: TodoRepository
) {
    // Unit is must for test function.
    @Test
    fun createTwice(): Unit = operator.runBlockingAndRollback {
        val todo = service.createTwice(false)
        assertThat(todo).isNotNull()
        assertThat(todoRepository.count()).isEqualTo(2)
    }

    // Service's transaction is failed. We can't wrap with runBlockingAndRollback.
    // Because it will treat whole method as one transaction and count will be 1.
    @Test
    fun createTwice_failed(): Unit = runBlocking {
        assertThrows<IllegalStateException> { service.createTwice(true) }
        assertThat(todoRepository.count()).isZero()
    }
}
