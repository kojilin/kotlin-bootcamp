package com.example.springweb.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.Clock
import java.time.Instant

@Repository
class TodoRepository(@Autowired val todoMapper: TodoMapper, val clock: Clock) {
    fun create(name: String, completed: Boolean): Todo {
        val entry = TodoEntry(null, name, completed, Instant.now(clock))
        todoMapper.insert(entry)
        return entry.toTodo()
    }

    fun createUsingXml(name: String, completed: Boolean): Todo {
        val entry = TodoEntry(null, name, completed, Instant.now(clock))
        todoMapper.insertUsingXml(entry)
        return entry.toTodo()
    }

    fun find(id: Long): Todo? {
        return todoMapper.find(id)?.toTodo()
    }
}

