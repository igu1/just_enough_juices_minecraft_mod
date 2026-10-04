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
        // MC 1.21.5 renamed several MobEffects fields. Only touch the MobEffects-qualified
        // names so our own JuicePower enum constants stay intact.
        string(current.parsed >= "1.21.5") {
            replace("MobEffects.MOVEMENT_SPEED", "MobEffects.SPEED")
            replace("MobEffects.MOVEMENT_SLOWDOWN", "MobEffects.SLOWNESS")
            replace("MobEffects.DAMAGE_BOOST", "MobEffects.STRENGTH")
            replace("MobEffects.DAMAGE_RESISTANCE", "MobEffects.RESISTANCE")
            replace("MobEffects.DIG_SPEED", "MobEffects.HASTE")
            replace("MobEffects.JUMP", "MobEffects.JUMP_BOOST")
            replace("MobEffects.CONFUSION", "MobEffects.NAUSEA")
        }
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}
