plugins {
    kotlin("multiplatform")
}

val tlPrebuilt = providers.gradleProperty("tl.prebuilt")
    .map { it.equals("true", ignoreCase = true) }
    .orElse(false)
val tlJarFile = rootProject.layout.projectDirectory
    .file("tl/build/libs/tl-jvm-${rootProject.version}.jar")
    .asFile

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            if (!tlPrebuilt.get()) {
                api(project(":tl"))
            }
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
        }
        jvmMain.dependencies {
            if (tlPrebuilt.get()) {
                api(files(tlJarFile))
            }
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

tasks.named("compileKotlinJvm") {
    doFirst {
        if (tlPrebuilt.get() && !tlJarFile.isFile) {
            error("tl.prebuilt=true, but ${tlJarFile.name} is missing. Run: ./gradlew :tl:jvmJar")
        }
    }
}

tasks.named<Test>("jvmTest") {
    jvmArgs("--add-opens=java.base/com.sun.crypto.provider=ALL-UNNAMED")
    filter {
        isFailOnNoMatchingTests = true
    }
    // Don't skip: benches and `--tests Class` would otherwise print BUILD SUCCESSFUL and do nothing.
    outputs.upToDateWhen { false }
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
    jvmArgs("--add-opens=java.base/com.sun.crypto.provider=ALL-UNNAMED")
}

fun JavaExec.testMain(cls: String, desc: String) {
    group = "application"
    description = desc
    val compilation = kotlin.targets.getByName("jvm").compilations.getByName("test")
    dependsOn(compilation.compileTaskProvider)
    classpath = compilation.output.allOutputs + (compilation.runtimeDependencyFiles ?: files())
    mainClass.set(cls)
    workingDir = rootProject.projectDir
    jvmArgs("--add-opens=java.base/com.sun.crypto.provider=ALL-UNNAMED")
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

tasks.register<JavaExec>("runFakeDc") {
    testMain("iris.kmtproto.FakeDcMainKt", "Fake DC server process (inbound bench)")
    jvmArgs("-Xmx2g")
}

tasks.register<JavaExec>("runDcBench") {
    testMain("iris.kmtproto.DcBenchMainKt", "Fake DC bench client (spawns FakeDc JVM)")
}

tasks.register<JavaExec>("runIgeBench") {
    testMain("iris.kmtproto.IgeThroughputTestKt", "Raw AES-256-IGE decrypt bench")
}

tasks.register<JavaExec>("runEventProcessorTest") {
    testMain("iris.kmtproto.EventProcessorTestKt", "Incoming event processor unit tests")
}


