plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":service"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // for illustration purposes, use h2 in-memory database
    developmentOnly(project(":libs:test-infra"))

    testImplementation(project(":libs:test-infra"))
}