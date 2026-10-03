# Just Enough Juices — NeoForge (Stonecutter)

This is a **separate build** from the Forge 1.18.2 project at the repository root.
Root `./gradlew runClient` runs **Forge 1.18.2**; it will never run NeoForge.

## Run

From this folder (`neoforge/`):

```bash
./gradlew :1.21.1:runClient      # NeoForge 1.21.1 client
./gradlew :1.21.1:runServer      # NeoForge 1.21.1 server
./gradlew :1.21.1:runData        # datagen for 1.21.1
./gradlew :1.21.1:build          # build the 1.21.1 jar
```

The run tasks live on the version subproject (`:1.21.1`), not on the root.

## Targets

Defined in `settings.gradle.kts` + `stonecutter.properties.toml`:

| Version | Status |
|---------|--------|
| 1.21.1 | compiles green |
| 1.21.11 | needs its own conditional pass |

Add/remove versions in `settings.gradle.kts` (`versions(...)`) and add a matching
section in `stonecutter.properties.toml` (`deps.neo_loader`, `mod.mc_compat`).

## IDE

- **VS Code:** open this `neoforge/` folder as the workspace root (not the repo
  root), then use the "1.21.1 - Client" launch configuration.
- **IntelliJ:** import/link `neoforge/` as its own Gradle project and run the
  `:1.21.1:runClient` task.

## Notes

- Requires the Gradle wrapper here (Gradle 9.3.1); the Forge project at the root
  uses its own Gradle 7.4 wrapper.
- Bush worldgen is currently disabled in the NeoForge port (the old
  `BiomeLoadingEvent` no longer exists); it should be re-added as a biome-modifier
  datapack JSON.
