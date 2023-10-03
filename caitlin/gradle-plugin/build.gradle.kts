import java.net.URI

plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.1.0"
    id("signing")
}

group = "io.github.strangepleasures.caitlin"
version = "0.1"

repositories {
    mavenCentral()
//    maven { url = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    mavenLocal()
}

dependencies {
    implementation("io.github.strangepleasures.caitlin:compiler:0.1:all")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java.sourceSets {
    getByName("main").java {
        srcDirs(
            "$projectDir/src/main/caitlin",
            "$projectDir/src/main/java-generated"
        )
    }
}

gradlePlugin {
    plugins {
        create("caitlinPlugin") {
            id = "io.github.strangepleasures.caitlin.gradle-plugin"
            displayName = "The Caitlin Programming Language"
            description = "The Caitlin Programming Language"
            implementationClass = "io.github.strangepleasures.caitlin.gradle.CaitlinPlugin"
            website = "https://github.com/strangepleasures/Caitlin"
            vcsUrl = "https://github.com/strangepleasures/Caitlin"
            tags = setOf("compiler", "programming language", "transpiler", "jvm", "java", "caitlin")
        }
    }
}

signing {
    useGpgCmd()
}