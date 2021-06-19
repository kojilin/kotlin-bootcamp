package com.example.springweb

import com.example.springweb.repository.AbstractContainerBaseTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [AbstractContainerBaseTest.Initializer::class])
class SpringWebApplicationTests {

    @Test
    fun contextLoads() {
    }

}
