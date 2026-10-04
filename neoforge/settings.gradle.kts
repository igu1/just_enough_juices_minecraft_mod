pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        // Major NeoForge releases only (no per-patch targets).
        versions(
            "1.20.4",
            "1.21.1",
            "1.21.4",
            "1.21.8",
            "1.21.11",
            "26.3"
        )
        vcsVersion = "1.21.1"
    }
}

rootProject.name = "Just Enough Juices NeoForge"
