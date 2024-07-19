dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-usecase"))
    implementation(project(":support:exception"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
