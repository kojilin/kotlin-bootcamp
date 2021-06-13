package com.example.springweb.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@TestConfiguration
class TestConfig {
    @Bean
    fun clock(): Clock {
        return Clock.fixed(Instant.parse("2018-12-04T14:01:23.00Z"), ZoneId.systemDefault())
    }
}
