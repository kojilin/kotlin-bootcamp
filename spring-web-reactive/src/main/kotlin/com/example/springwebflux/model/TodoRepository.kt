package com.example.springwebflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

interface TodoRepository : CoroutineCrudRepository<Todo, Long> {
}

class Todo(@Id var id: Long?, val name: String, @Column("created_at") val createdAt: Instant) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Todo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
