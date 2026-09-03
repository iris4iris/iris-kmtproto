plugins {
    kotlin("multiplatform") version "2.1.20" apply false
}

allprojects {
    group = "iris.kmtproto"
    version = "0.1.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

tasks.register("generateTl") {
    group = "tlgen"
    description = "Generate Kotlin TL types in :tl"
    dependsOn(":tl:generateTl")
}

tasks.register("jvmRun") {
    group = "application"
    description = "Echo bot — delegates to :core:jvmRun"
    dependsOn(":core:jvmRun")
}
