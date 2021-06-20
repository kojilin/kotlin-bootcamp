package com.example.springktor.config

import com.example.springktor.client.RandomClient
import com.example.springktor.config.RootConfig.RootConfig.DEFAULT_LENGTH_OF_RANDOM
import com.example.springktor.config.RootConfig.RootConfig.gracePeriodSeconds
import com.example.springktor.config.RootConfig.RootConfig.timeoutSeconds
import com.example.springktor.service.ExampleService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.time.Clock
import java.time.Duration


@Configuration
class RootConfig {
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }

    @Bean
    fun client(): RandomClient {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
        return retrofit.create(RandomClient::class.java)
    }

    @Bean
    fun helloRoute(exampleService: ExampleService): RoutingDecorator {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return object : RoutingDecorator {
            override fun apply(application: Application) {
                application.routing {
                    get("/hello/{throw}") {
                        val throwException = call.parameters["throw"]?.toBoolean() ?: false
                        val todo = exampleService.createTwice(throwException)
                        call.respondText("hello: $todo")
                    }
                    get("/api/random/") {
                        val randomString = (1..(call.parameters["n"]?.toInt() ?: DEFAULT_LENGTH_OF_RANDOM))
                            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                            .map(charPool::get)
                            .joinToString("")
                        call.respondText(randomString)
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
        }.start(), gracePeriodSeconds, timeoutSeconds)
    }

    object RootConfig {
        const val gracePeriodSeconds: Long = 15
        const val timeoutSeconds: Long = 30
        const val DEFAULT_LENGTH_OF_RANDOM = 5
    }
}

class ServerWrapper(
    private val engine: ApplicationEngine,
    private val gracePeriodSeconds: Long,
    private val timeoutSeconds: Long
) :
    AutoCloseable {
    override fun close() {
        engine.stop(
            Duration.ofSeconds(gracePeriodSeconds).toMillis(),
            Duration.ofSeconds(timeoutSeconds).toMillis()
        )
    }
}

interface RoutingDecorator {
    fun apply(application: Application)
}
