plugins {
    id("com.epages.restdocs-api-spec")
}

val jwtVersion: String by project
val springRestDocsVersion: String by project
val applicationVersion: String by project

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure:persistence"))
    implementation(project(":infrastructure:redis"))
    implementation(project(":infrastructure:smtp"))
    implementation(project(":common:exception"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:$springRestDocsVersion")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
}

openapi3 {
    title = "Stalk Member Service Docs"
    description = "Member, Follow APIs"
    version = applicationVersion
    format = "json"
    outputDirectory = "build/docs"
    outputFileNamePrefix = "member-service"
}
