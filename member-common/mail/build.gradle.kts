dependencies {
    implementation("org.springframework.boot:spring-boot-starter-mail")
}

tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}
