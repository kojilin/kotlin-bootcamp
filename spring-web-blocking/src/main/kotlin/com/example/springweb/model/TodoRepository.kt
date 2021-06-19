package com.example.springweb.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.Clock
import java.time.Instant

@Repository
class TodoRepository(@Autowired val todoMapper: TodoMapper, val clock: Clock) {
    fun create(name: String, completed: Boolean): Todo {
        val keyHolder = KeyHolder(null)
        todoMapper.insert(name, completed, Instant.now(clock), keyHolder)
        return keyHolder.key?.let {
            todoMapper.find(it)
        } ?: throw  IllegalStateException("Failed to insert")
    }

    fun createUsingXml(name: String, completed: Boolean): Todo {
        val keyHolder = KeyHolder(null)
        todoMapper.insertUsingXml(name, completed, Instant.now(clock), keyHolder)
        return keyHolder.key?.let {
            todoMapper.find(it)
        } ?: throw  IllegalStateException("Failed to insert")
    }

    fun find(id: Long): Todo? {
        return todoMapper.find(id)
    }
}
