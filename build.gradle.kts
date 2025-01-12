plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api("javax.inject:javax.inject:1")

    implementation("com.google.dagger:dagger-compiler:2.51.1")
    // ksp("com.google.dagger:dagger-compiler:2.51.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
