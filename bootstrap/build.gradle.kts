dependencies {
    implementation(project(":presentation"))

    implementation(project(":support:monitor"))
    implementation(project(":support:msa-core"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
