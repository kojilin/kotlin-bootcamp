package com.example.springweb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class RootConfig {
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }
}
