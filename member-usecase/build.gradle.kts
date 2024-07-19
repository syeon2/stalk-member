dependencies {
    implementation(project(":member-domain"))

    implementation("org.springframework:spring-context")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
