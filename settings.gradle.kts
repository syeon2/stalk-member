rootProject.name = "member-service"

include(
    "bootstrap",
    "member-api",
    "member-domain",
    "member-usecase",
    "member-infrastructure:persistence",
    "member-infrastructure:redis",
    "member-common:exception",
    "member-common:mail",
    "member-common:encrypt",
    "support:msa-core",
    "support:monitor"
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val ktlintVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
            }
        }
    }
}
