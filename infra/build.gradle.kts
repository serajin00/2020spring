
dependencies {
    implementation(project(":domain"))
    implementation(project(":dto"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("dev.miku:r2dbc-mysql")

    testImplementation(project(":libs:test-infra"))
    testImplementation(project(":libs:test-models"))
}
