
dependencies {
    api("org.springframework.data:spring-data-commons")

    testImplementation(project(":libs:test-models"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}