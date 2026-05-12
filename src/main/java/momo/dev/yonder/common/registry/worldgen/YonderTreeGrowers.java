package momo.dev.yonder.common.registry.worldgen;

import momo.dev.yonder.Yonder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class YonderTreeGrowers {
    public static final TreeGrower DEAD = new TreeGrower(
            "dead",
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, Yonder.loc("trees/oak/dying_variants/1"))),
            Optional.empty()
    );
}
