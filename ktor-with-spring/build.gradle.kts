plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

dependencies {
    // demo sub module dependency
    implementation(project(":plain-project"))

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("dev.miku:r2dbc-mysql")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")

    // For test container
    testRuntimeOnly("mysql:mysql-connector-java")
    testRuntimeOnly("org.flywaydb:flyway-core")
    testRuntimeOnly("org.springframework.boot:spring-boot-starter-jdbc")

}
