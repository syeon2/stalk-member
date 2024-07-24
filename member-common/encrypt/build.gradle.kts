dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
