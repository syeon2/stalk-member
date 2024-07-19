dependencies {
    implementation(project(":member-domain"))
    implementation(project(":support:exception"))

    implementation("org.springframework:spring-context")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
