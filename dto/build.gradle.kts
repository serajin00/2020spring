
dependencies {
    api(project(":domain"))

    api("org.springframework.boot:spring-boot-starter-json")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(project(":libs:test-models"))
    testImplementation("org.springframework:spring-web")
}
