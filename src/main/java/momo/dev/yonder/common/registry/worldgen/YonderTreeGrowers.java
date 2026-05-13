package momo.dev.yonder.common.registry.worldgen;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class YonderTreeGrowers {
    public static final TreeGrower ASPEN = new TreeGrower("aspen", 0f,
            Optional.empty(),
            Optional.empty(),
            Optional.of(YonderConfiguredFeatures.ASPEN),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower BIRCH = new TreeGrower("birch", 0f,
            Optional.empty(),
            Optional.empty(),
            Optional.of(YonderConfiguredFeatures.BIRCH),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower PEAR = new TreeGrower("pear", 0f,
            Optional.empty(),
            Optional.empty(),
            Optional.of(YonderConfiguredFeatures.PEAR),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
}
