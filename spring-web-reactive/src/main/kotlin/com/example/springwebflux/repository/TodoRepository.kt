package com.example.springwebflux.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

interface TodoRepository : CoroutineCrudRepository<Todo, Long> {
}

data class Todo(@Id var id: Long?, val name: String, @Column("created_at") val createdAt: Instant)
