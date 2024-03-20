package net.atired.thedefused.worldgen;
import net.atired.thedefused.DefusedMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_SHALE = registerKey("add_shale");
    public static void bootstrap(BootstapContext<BiomeModifier> context) {

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        context.register(ADD_SHALE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.HAS_BASTION_REMNANT),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeaturesClass.SHALE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));
    }
    private static ResourceKey<BiomeModifier> registerKey(String name) {

        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(DefusedMod.MODID, name));
    }

}
