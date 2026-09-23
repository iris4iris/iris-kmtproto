plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        compilations.create("botgen")
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

val botgenCompilation = kotlin.targets.getByName("jvm").compilations.getByName("botgen")

kotlin.sourceSets.getByName("jvmBotgen").dependencies {
    implementation("org.json:json:20240303")
}

tasks.register<JavaExec>("generateBotApi") {
    group = "botgen"
    description = "Regenerate Bot API types from schema/api.min.json"
    dependsOn(botgenCompilation.compileTaskProvider)
    classpath = botgenCompilation.output.allOutputs + (botgenCompilation.runtimeDependencyFiles ?: files())
    mainClass.set("iris.kmtproto.botgen.GenerateBotApiKt")
    workingDir = projectDir
}
