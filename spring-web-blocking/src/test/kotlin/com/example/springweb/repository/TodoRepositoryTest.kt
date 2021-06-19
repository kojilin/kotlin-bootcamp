package com.example.springweb.repository

import com.example.springweb.config.TestConfig
import com.example.springweb.model.TodoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import java.time.Instant

// This annotation does a lot of thing. For example, rollback, setting in-memory-db.
@MybatisTest
// Don't use in-memory DBO
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
@Import(TestConfig::class, TodoRepository::class)
class TodoRepositoryTest(@Autowired val target: TodoRepository) {

    @Test
    fun create() {
        val todo = target.create("Foo", false)
        val found = target.find(todo.id)
        assertThat(found?.name).isEqualTo("Foo")
        assertThat(found?.createdAt).isEqualTo(Instant.parse("2018-12-04T14:01:23.00Z"))
    }

    @Test
    fun create_usingXml() {
        val todo = target.createUsingXml("Foo", false)
        val found = target.find(todo.id)
        assertThat(found?.name).isEqualTo("Foo")
        assertThat(found?.createdAt).isEqualTo(Instant.parse("2018-12-04T14:01:23.00Z"))
    }
}

