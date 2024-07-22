dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}
