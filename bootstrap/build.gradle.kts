dependencies {
    implementation(project(":member-api"))
    implementation(project(":member-infrastructure:persistence"))
    implementation(project(":member-common:mail"))
    implementation(project(":support:monitor"))
    implementation(project(":support:msa-core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
