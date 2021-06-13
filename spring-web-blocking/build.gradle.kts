plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

dependencies {
    // demo sub module dependency
    implementation(project(":plain-project"))

    // demo force version specify
    implementation("org.apache.commons:commons-lang3")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // This imports other spring's jdbc starter.
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("org.flywaydb:flyway-core")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test")
}
