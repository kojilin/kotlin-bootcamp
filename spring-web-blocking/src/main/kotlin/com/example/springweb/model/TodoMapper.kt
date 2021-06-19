package com.example.springweb.model

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import java.time.Instant

@Mapper
interface TodoMapper {
    @Insert("INSERT INTO todo (name, completed, created_at) VALUES(#{name}, #{completed}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(todo: TodoEntry): Int

    // From xml
    fun insertUsingXml(todo: TodoEntry): Int

    @Select("SELECT * from todo where id=#{id}")
    fun find(id: Long): TodoEntry?
}

data class TodoEntry(var id: Long?, val name: String, val completed: Boolean, val createdAt: Instant) {
    fun toTodo(): Todo {
        return id?.let {
            return Todo(it, name, completed, createdAt)
        } ?: throw IllegalStateException("Can't convert to Todo")
    }
}
