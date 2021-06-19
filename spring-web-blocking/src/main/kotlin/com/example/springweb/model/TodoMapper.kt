package com.example.springweb.model

import org.apache.ibatis.annotations.*
import java.time.Instant

@Mapper
interface TodoMapper {
    @Insert("INSERT INTO todo (name, completed, created_at) VALUES(#{name}, #{completed}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "keyHolder.key")
    fun insert(
        name: String,
        completed: Boolean,
        createdAt: Instant,
        @Param("keyHolder") keyHolder: KeyHolder
    ): Int

    // From xml
    fun insertUsingXml(
        name: String,
        completed: Boolean,
        createdAt: Instant,
        @Param("keyHolder") keyHolder: KeyHolder
    ): Int

    @Select("SELECT * from todo where id=#{id}")
    fun find(id: Long): Todo?
}

data class KeyHolder(var key: Long?)
