dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-common:mail"))
    implementation(project(":member-common:exception"))
    implementation(project(":member-common:encrypt"))

    implementation("org.springframework:spring-context")
}
