import groovy.lang.Closure

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.5.10"
        kotlin("plugin.spring") version "1.5.10"
        id("org.springframework.boot") version "2.5.1"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
    }
}

rootProject.name = "kotlin-bootcamp"

apply(from = "${rootDir}/gradle/scripts/settings-flags.gradle")

val includeWithFlags: Closure<Any> by extra

includeWithFlags("spring-web-blocking", "kotlin", "spring")
includeWithFlags("spring-web-reactive", "kotlin", "spring")
includeWithFlags("ktor-with-spring", "kotlin", "spring")
includeWithFlags("plain-project", "java")
