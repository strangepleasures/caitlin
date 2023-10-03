import java.net.URI

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "io.github.strangepleasures.caitlin"
version = "0.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
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

java {
    withJavadocJar()
    withSourcesJar()
}


publishing {
    publications {

        create<MavenPublication>("maven") {

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
            pom {
                name = "Caitlin StcLib"
                description = "The standard library for the Caitlin programming language"
                url = "https://github.com/strangepleasures/caitlin"
                inceptionYear = "2023"

                developers {
                    developer {
                        id = "strangepleasures"
                        name = "Pavel Mikhailovskii"
                        email = "pavel.mikhailovskii@gmail.com"
                    }
                }

                licenses {
                    license {
                        name = "GNU Lesser General Public License"
                    }

                    license {
                        name = "Apache License"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/strangepleasures/caitlin.git"
                    developerConnection = "scm:git:ssh://github.com/strangepleasures/caitlin.git"
                    url = "https://github.com/strangepleasures/caitlin"
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            // url = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.properties["sonatype.username"].toString()
                password = project.properties["sonatype.password"].toString()
            }
        }
    }
}


signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}
