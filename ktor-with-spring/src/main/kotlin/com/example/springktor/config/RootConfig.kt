package com.example.springktor.config

import com.example.springktor.service.ExampleService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class RootConfig {
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }

    @Bean
    fun helloRoute(exampleService: ExampleService): RoutingDecorator {
        return object : RoutingDecorator {
            override fun apply(application: Application) {
                application.routing {
                    get("/hello/{throw}") {
                        val throwException = call.parameters["throw"]?.toBoolean() ?: false
                        val todo = exampleService.createTwice(throwException)
                        call.respondText("hello: $todo")
                    }
                }
            }
        }
    }

    @Bean
    fun engine(routingDecorators: List<RoutingDecorator>): ServerWrapper {
        return ServerWrapper(embeddedServer(Netty, port = 8080) {
            for (routingDecorator in routingDecorators) {
                routingDecorator.apply(this)
            }
        }.start())
    }
}

class ServerWrapper(private val engine: ApplicationEngine) : AutoCloseable {
    override fun close() {
        engine.stop(15 * 1000, 30 * 1000)
    }
}

interface RoutingDecorator {
    fun apply(application: Application)
}
