package com.example.springwebflux.repository

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
                // Timezone
                withConfigurationOverride("db/mysql_conf_override")
                // For all test class
                start()
            }
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.r2dbc.url=${
                    mysql.jdbcUrl.replace(
                        "jdbc",
                        "r2dbc"
                    )
                }",
                "spring.r2dbc.username=${mysql.username}",
                "spring.r2dbc.password=${mysql.password}",
                // Due to flyway is not integrated with r2dbc.
                "spring.flyway.url=${mysql.jdbcUrl}",
                "spring.flyway.user=${mysql.username}",
                "spring.flyway.password=${mysql.password}",
            ).applyTo(applicationContext.environment)
        }
    }
}
