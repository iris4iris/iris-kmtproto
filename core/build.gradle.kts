plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

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

tasks.register<JavaExec>("runEcho") {
    exampleMain("iris.kmtproto.example.EchoBotMainKt", "Echo bot (BotApi)")
}

tasks.register<JavaExec>("runBotApi") {
    exampleMain("iris.kmtproto.example.BotApiExampleMainKt", "BotApi example: login, send, listen")
}

tasks.register<JavaExec>("runUserApi") {
    exampleMain("iris.kmtproto.example.UserApiExampleMainKt", "UserApi example: SMS login, send, listen")
}
