
dependencies {
    api(project(":infra"))
    api(project(":domain"))

    runtimeOnly("io.r2dbc:r2dbc-h2")
    implementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j")
    }

    api("org.springframework:spring-context")
    api("org.springframework:spring-test")
    api("org.springframework.data:spring-data-r2dbc")
}