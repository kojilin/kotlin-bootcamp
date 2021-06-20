package com.example.springwebflux.model

import com.example.springwebflux.config.TestConfig
import com.example.springwebflux.runBlockingAndRollback
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.reactive.TransactionalOperator
import java.time.Clock
import java.time.Instant

@DataR2dbcTest
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
@Import(TestConfig::class)
class TodoRepositoryTest(
    @Autowired private val target: TodoRepository,
    @Autowired private val operator: TransactionalOperator,
    @Autowired private val clock: Clock
) {
    @Test
    fun create(): Unit = operator.runBlockingAndRollback {
        val todo = Todo(null, "Foo", Instant.now(clock))
        target.save(todo)

        assertThat(todo.id).isNotNull

        val found = target.findById(todo.id!!)
        assertThat(found?.name).isEqualTo("Foo")
        assertThat(found?.createdAt).isEqualTo(Instant.parse("2018-12-04T14:01:23.00Z"))
    }
}

