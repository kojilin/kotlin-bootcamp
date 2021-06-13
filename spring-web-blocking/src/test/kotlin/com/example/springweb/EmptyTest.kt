package com.example.springweb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmptyTest {
    @Test
    fun empty() {
        assertThat("1").isEqualTo("1")
    }
}
