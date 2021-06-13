package com.example.springweb.repository

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import java.time.Instant

@Mapper
interface TodoRepository {
    @Insert("INSERT INTO todo (name, created_at) VALUES(#{name}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(todo: Todo): Int

    // From xml
    fun insertUsingXml(todo: Todo): Int

    @Select("SELECT * from todo where id=#{id}")
    fun find(id: Long): Todo
}

data class Todo(var id: Long?, val name: String, val createdAt: Instant)
