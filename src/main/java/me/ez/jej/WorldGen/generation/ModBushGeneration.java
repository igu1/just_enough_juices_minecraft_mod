package me.ez.jej.WorldGen.generation;

import me.ez.jej.WorldGen.ModPlacementFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;

public class ModBushGeneration {
    public static void generateBush(final BiomeLoadingEvent e){
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, e.getName());

        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if (types.contains(BiomeDictionary.Type.SNOWY)){
            List<Holder<PlacedFeature>> base = e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);

            base.add(ModPlacementFeature.PATCH_ICE_BUSH);

        }else if (types.contains(BiomeDictionary.Type.SWAMP)){
            List<Holder<PlacedFeature>> base = e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
            base.add(ModPlacementFeature.PATCH_WILD_BUSH);

        }else if (types.contains(BiomeDictionary.Type.HOT) && types.contains(BiomeDictionary.Type.SANDY)){
            List<Holder<PlacedFeature>> base = e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
            base.add(ModPlacementFeature.PATCH_SUN_BUSH);

        }else if (types.contains(BiomeDictionary.Type.MOUNTAIN) || types.contains(BiomeDictionary.Type.RIVER)){
            List<Holder<PlacedFeature>> base = e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
            base.add(ModPlacementFeature.PATCH_GLOW_BUSH);
        }
    }
}
