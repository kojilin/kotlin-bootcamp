import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") apply false
    id("org.springframework.boot") apply false
    id("org.jetbrains.kotlin.plugin.spring") apply false
    id("io.spring.dependency-management")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gradle:osdetector-gradle-plugin:1.6.2")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.8.RELEASE")
    }
}

apply(from = "${rootDir}/gradle/scripts/build-flags.gradle")
val projectsWithFlags: groovy.lang.Closure<Iterable<Project>> by extra


allprojects {
    val flags: Any by project
    println("Project '${project.path}' has flags: $flags")
    repositories {
        mavenCentral()
    }
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
}

configure(projectsWithFlags("kotlin")) {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        "implementation"(platform("org.jetbrains.kotlin:kotlin-bom"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
    }

    // Apply all open for spring project.
    val hasFlags: groovy.lang.Closure<Boolean> by this
    if (hasFlags("spring")) {
        apply {
            plugin("org.jetbrains.kotlin.plugin.spring")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
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
                entry("ktor-client-core")
                entry("ktor-client-apache")
                entry("ktor-server-core")
                entry("ktor-server-netty")
            }
            dependency("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
