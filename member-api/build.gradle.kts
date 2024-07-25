val jwtVersion: String by project

dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-usecase"))
    implementation(project(":member-infrastructure:persistence"))
    implementation(project(":member-infrastructure:redis"))
    implementation(project(":member-common:exception"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
}
