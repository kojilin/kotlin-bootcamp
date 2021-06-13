package com.example.springweb.repository

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

/**
 * Because MySQL has transaction. We can use single container.
 */
abstract class AbstractContainerBaseTest {
    companion object {
        val mysql: MySQLContainer<*> = MySQLContainer<Nothing>(DockerImageName.parse("mysql"))
            .apply {
                withUsername("test")
                withPassword("test")
                withDatabaseName("unit_test_db")
                withConfigurationOverride("db/mysql_conf_override")
                // For all test class
                start()
            }
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${mysql.jdbcUrl}",
                "spring.datasource.username=${mysql.username}",
                "spring.datasource.password=${mysql.password}"
            ).applyTo(applicationContext.environment)
        }
    }
}
