plugins {
    kotlin("jvm") version "1.9.20"
}

group = "io.jcurtis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.cloudburstmc.protocol:bedrock-connection:3.0.0.Beta1-SNAPSHOT")
    implementation("org.slf4j:slf4j-log4j12:1.7.29")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}