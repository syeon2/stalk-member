dependencies {
    implementation(project(":member-api"))
    implementation(project(":member-infrastructure:persistence"))
    implementation(project(":support:monitor"))
    implementation(project(":support:msa-core"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
