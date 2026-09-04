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
        events("passed", "skipped", "failed", "standardOut")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
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

fun JavaExec.exampleMain(cls: String, desc: String) {
    group = "application"
    description = desc
    val compilation = kotlin.targets.getByName("jvm").compilations.getByName("main")
    dependsOn(compilation.compileTaskProvider)
    classpath = compilation.output.allOutputs + (compilation.runtimeDependencyFiles ?: files())
    mainClass.set(cls)
    workingDir = rootProject.projectDir
    standardInput = System.`in`
}

tasks.register<JavaExec>("runBotApi") {
    exampleMain("iris.kmtproto.example.BotApiExampleMainKt", "BotApi example: login, send, listen")
}

tasks.register<JavaExec>("runUserApi") {
    exampleMain("iris.kmtproto.example.UserApiExampleMainKt", "UserApi example: SMS login, send, listen")
}
