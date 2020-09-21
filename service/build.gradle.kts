dependencies {
    api(project(":domain"))
    api(project(":dto"))
    api(project(":infra"))

    testImplementation(project(":libs:test-infra"))
    testImplementation(project(":libs:test-models"))
}
