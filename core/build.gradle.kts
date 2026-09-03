plugins {
    kotlin("multiplatform")
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
            api(project(":tl"))
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
    filter {
        isFailOnNoMatchingTests = true
    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.register<JavaExec>("jvmRun") {
    group = "application"
    description = "Echo bot — same main as runJvm, name IDEA expects"
    val compilation = kotlin.targets.getByName("jvm").compilations.getByName("main")
    dependsOn(compilation.compileTaskProvider)
    classpath = compilation.output.allOutputs + (compilation.runtimeDependencyFiles ?: files())
    mainClass.set("iris.kmtproto.example.EchoBotMainKt")
    workingDir = rootProject.projectDir
    standardInput = System.`in`
}
