dependencies {
    implementation(project(":member-usecase"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}
