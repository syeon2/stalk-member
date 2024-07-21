dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-common:mail"))
    implementation(project(":member-common:exception"))
    implementation(project(":support:security"))

    implementation("org.springframework:spring-context")
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
