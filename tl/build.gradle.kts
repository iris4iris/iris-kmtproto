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
            kotlin.srcDir(generatedTlRoot)
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
    outputs.dir(generatedTlRoot)
    inputs.file(layout.projectDirectory.file("schema/api.tl"))
    inputs.files(tlgenCompilation.output.allOutputs)
    doFirst {
        val dir = generatedTlPkg.get().asFile
        dir.mkdirs()
        args = listOf(dir.absolutePath)
    }
}

tasks.matching {
    it.name.startsWith("compile") && it.name.contains("Kotlin") && !it.name.contains("Tlgen", ignoreCase = true)
}.configureEach {
    dependsOn("generateTl")
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
