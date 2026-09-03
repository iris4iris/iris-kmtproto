plugins {
    kotlin("multiplatform") version "2.1.20"
}

group = "iris.kmtproto"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        binaries {
            executable {
                mainClass.set("iris.kmtproto.example.EchoBotMainKt")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

tasks.named<Test>("jvmTest") {
    // Do not exclude LiveTest here. IntelliJ's TestLauncher asks for
    // HandshakeLiveTest.handshakeAndPingDc2; an exclude of *LiveTest makes that
    // pattern match nothing.
    filter {
        isFailOnNoMatchingTests = true
    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

// IDEA Debug on EchoBotMain.kt often invokes :jvmRun (not :runJvm).
tasks.register<JavaExec>("jvmRun") {
    group = "application"
    description = "Echo bot — same main as runJvm, name IDEA expects"
    val compilation = kotlin.targets.getByName("jvm").compilations.getByName("main")
    dependsOn(compilation.compileTaskProvider)
    classpath = compilation.runtimeDependencyFiles?.let { compilation.output.allOutputs + it } ?: compilation.output.allOutputs
    mainClass.set("iris.kmtproto.example.EchoBotMainKt")
    standardInput = System.`in`
}

tasks.register<JavaExec>("generateTlDemo") {
    group = "tlgen"
    description = "Generate Kotlin TL types from schema/api.tl"
    val compilation = kotlin.targets.getByName("jvm").compilations.getByName("main")
    dependsOn(compilation.compileTaskProvider)
    classpath = compilation.runtimeDependencyFiles?.let { compilation.output.allOutputs + it } ?: compilation.output.allOutputs
    mainClass.set("iris.kmtproto.tlgen.GenerateDemoKt")
    workingDir = projectDir
}
