package momo.dev.yonder.common.registry;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LeavesBlock;

public class YonderFlammables {
    public static void register() {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;

        YonderBlocks.BLOCK_DEFINITIONS.forEach(block -> {
            if (block.get() instanceof LeavesBlock) {
                fireBlock.setFlammable(block.get(), 30, 60);
            }
        });

    }
}
