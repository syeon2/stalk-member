rootProject.name = "member-service"

include(
    "bootstrap",
    "presentation",
    "domain",
    "application",
    "infrastructure:persistence",
    "infrastructure:redis",
    "infrastructure:smtp",
    "common:exception",
    "common:encrypt",
    "support:msa-core",
    "support:monitor"
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val ktlintVersion: String by settings
    val springRestDocsVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
                "com.epages.restdocs-api-spec" -> useVersion(springRestDocsVersion)
            }
        }
    }
}
