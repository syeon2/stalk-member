dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-usecase"))
    implementation(project(":member-common:exception"))
    implementation(project(":member-common:encrypt"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
