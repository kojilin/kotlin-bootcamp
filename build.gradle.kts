import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10" apply false
    kotlin("plugin.spring") version "1.5.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            dependencySet("org.mybatis.spring.boot:2.2.0") {
                entry("mybatis-spring-boot-starter")
                entry("mybatis-spring-boot-starter-test")
            }
            dependencySet("org.testcontainers:1.15.3") {
                entry("mysql")
                entry("junit-jupiter")
            }
            dependencySet("io.ktor:1.6.0") {
                entry("ktor-server-core")
                entry("ktor-server-netty")
            }
            dependency("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}
