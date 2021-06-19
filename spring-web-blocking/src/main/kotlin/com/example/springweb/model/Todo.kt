package com.example.springweb.model

import java.time.Instant

class Todo(val id: Long, val name: String, val completed: Boolean, val createdAt: Instant) {
    fun complete(): Todo {
        return Todo(id, name, true, createdAt)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Todo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
