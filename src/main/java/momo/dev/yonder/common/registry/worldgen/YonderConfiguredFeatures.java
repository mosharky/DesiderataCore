package momo.dev.yonder.common.registry.worldgen;

import momo.dev.yonder.Yonder;
import net.hecco.bountifulfares.BountifulFares;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class YonderConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN = register("aspen");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH = register("birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEAR = register("pear");

    public static ResourceKey<ConfiguredFeature<?, ?>> register(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Yonder.loc(name));
    }
}
