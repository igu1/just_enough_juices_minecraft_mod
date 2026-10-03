plugins {
    id("dev.kikugie.stonecutter")
    id("net.neoforged.moddev") version "2.0.147" apply false
}

stonecutter active "1.21.1"

// Build + collect every declared version into the repository builds/ folder.
tasks.register("buildAll") {
    group = "build"
    description = "Builds and collects jars for every declared NeoForge version."
    dependsOn(subprojects.map { "${it.path}:buildAndCollect" })
}

stonecutter parameters {
    swaps["mod_version"] = "\"${properties.get<String>("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = true

    replacements {
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}
