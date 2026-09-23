plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

tasks.register<Exec>("generateBotApi") {
    group = "botgen"
    description = "Regenerate Bot API types from schema/api.min.json"
    workingDir = projectDir
    commandLine("python3", "gen/generate_bot_api.py")
}
