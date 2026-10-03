plugins {
    id("net.neoforged.moddev")
    id("neoforge-mutex")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

val requiredJava = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    else -> JavaVersion.VERSION_17
}

repositories {
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
}

dependencies {
    // Add NeoForge mod dependencies here.
}

neoForge {
    version = property("deps.neo_loader") as String

    mods {
        register("jej") {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        // Pass -PdebugJvm to expose a JDWP debug port (5005) for attaching a debugger.
        val debugAgent = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
        val debug = project.findProperty("debugJvm") != null
        register("client") {
            gameDirectory = file("../run-neoforge/")
            client()
            if (debug) jvmArgument(debugAgent)
        }
        register("server") {
            gameDirectory = file("../run-neoforge/")
            server()
            if (debug) jvmArgument(debugAgent)
        }
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }
        val props = buildMap {
            register("id", sc.properties["mod.id"])
            register("name", sc.properties["mod.name"])
            register("version", sc.properties["mod.version"])
            register("minecraft", sc.properties["mod.mc_compat"])
        }
        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    withType<Jar> {
        val name = project.property("mod.id")
        inputs.property("mod_id", name)
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to the repository builds/neoforge-<version>/ folder"
        inputs.property("version", project.property("mod.version"))
        from(jar.flatMap { it.archiveFile }, named<Jar>("sourcesJar").flatMap { it.archiveFile })
        into(rootProject.projectDir.parentFile.resolve("builds/neoforge-${sc.current.version}"))
        rename { name ->
            val suffix = if (name.contains("-sources")) "-sources.jar" else ".jar"
            "jej-neoforge-${sc.current.version}-${project.property("mod.version")}$suffix"
        }
    }
}
