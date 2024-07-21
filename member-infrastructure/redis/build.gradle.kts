dependencies {
    implementation(project(":member-usecase"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}
