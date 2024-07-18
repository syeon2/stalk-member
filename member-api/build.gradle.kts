dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}
