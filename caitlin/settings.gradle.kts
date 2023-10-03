rootProject.name = "caitlin"


pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}
include("compiler")
include("stdlib")
include("gradle-plugin")
