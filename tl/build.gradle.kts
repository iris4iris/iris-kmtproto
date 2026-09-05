import org.gradle.api.tasks.PathSensitivity

plugins {
    kotlin("multiplatform")
}

val generatedTlRoot = layout.buildDirectory.dir("generated/tl/kotlin")
val generatedTlPkg = layout.buildDirectory.dir("generated/tl/kotlin/iris/kmtproto/tl/gen")

kotlin {
    jvm {
        compilations.create("tlgen")
    }

    sourceSets {
        commonMain {
            // generated sources attached after generateTl is registered (builtBy)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

val tlgenCompilation = kotlin.targets.getByName("jvm").compilations.getByName("tlgen")

kotlin.sourceSets.getByName("jvmTest").dependencies {
    implementation(files(tlgenCompilation.output.allOutputs))
}

tasks.named("compileTestKotlinJvm") {
    dependsOn(tlgenCompilation.compileTaskProvider)
}

tasks.register<JavaExec>("generateTl") {
    group = "tlgen"
    description = "Generate Kotlin TL types from schema/api.tl into build/generated"
    dependsOn(tlgenCompilation.compileTaskProvider)
    classpath = tlgenCompilation.output.allOutputs + (tlgenCompilation.runtimeDependencyFiles ?: files())
    mainClass.set("iris.kmtproto.tlgen.GenerateTlKt")
    workingDir = projectDir
    args(generatedTlPkg.get().asFile.absolutePath)
    inputs.file(layout.projectDirectory.file("schema/api.tl")).withPathSensitivity(PathSensitivity.RELATIVE)
    outputs.dir(generatedTlPkg)
}

kotlin.sourceSets.getByName("commonMain").kotlin.srcDir(
    files(generatedTlRoot).builtBy(tasks.named("generateTl")),
)

tasks.named<Test>("jvmTest") {
    filter {
        isFailOnNoMatchingTests = true
    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
