plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "io.github.strangepleasures.caitlin"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

