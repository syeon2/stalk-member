dependencies {
    implementation(project(":member-domain"))
    implementation(project(":member-usecase"))

    // SQL
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // DB
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.h2database:h2")

    // Util
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

val queryDslDir = "$buildDir/genreated/querydsl"

sourceSets {
    getByName("main").java.srcDirs(queryDslDir)
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory = file(queryDslDir)
}

tasks.named("clean") {
    doLast {
        file(queryDslDir).deleteRecursively()
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.getByName("jar").enabled = true
tasks.getByName("bootJar").enabled = false
