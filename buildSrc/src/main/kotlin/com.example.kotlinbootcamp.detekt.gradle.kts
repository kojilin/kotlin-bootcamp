import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "11"
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
    }
}
