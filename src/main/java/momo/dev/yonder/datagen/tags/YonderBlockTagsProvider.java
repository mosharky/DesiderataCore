package momo.dev.yonder.datagen.tags;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.registry.YonderTags;
import net.hecco.bountifulfares.definition.block.custom.FruitLogBlock;
import net.hecco.bountifulfares.definition.block.custom.StrippedFruitLogBlock;
import net.hecco.bountifulfares.registry.tags.BFBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static momo.dev.yonder.common.registry.YonderBlocks.*;

public class YonderBlockTagsProvider extends BlockTagsProvider {
    public YonderBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Yonder.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (BlockDefinition<?> def : BLOCK_DEFINITIONS) {
            Block block = def.block();
            if (def.get() instanceof SaplingBlock) tag(BlockTags.SAPLINGS).add(block);
            if (def.get() instanceof LeavesBlock) tag(BlockTags.LEAVES).add(block);
            if (def.get() instanceof FruitLogBlock) {
                tag(BFBlockTags.IGNORE_PARTICLE_TINT).add(block);
                tag(BlockTags.LOGS_THAT_BURN).add(block);
                tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            }
            if (def.get() instanceof StrippedFruitLogBlock) {
                tag(BlockTags.LOGS_THAT_BURN).add(block);
                tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            }
            if (def.get() instanceof FlowerPotBlock)
                tag(BlockTags.FLOWER_POTS).add(block);
        }

        tag(YonderTags.Blocks.ASPEN_LOGS).add(ASPEN_LOG.block(), STRIPPED_ASPEN_LOG.block(), ASPEN_WOOD.block(), STRIPPED_ASPEN_WOOD.block());
        tag(YonderTags.Blocks.BIRCH_LOGS).add(BIRCH_LOG.block(), STRIPPED_BIRCH_LOG.block(), BIRCH_WOOD.block(), STRIPPED_BIRCH_LOG.block());
        tag(YonderTags.Blocks.PEAR_LOGS).add(PEAR_LOG.block(), STRIPPED_PEAR_LOG.block(),  PEAR_WOOD.block(), STRIPPED_PEAR_LOG.block());
        tag(YonderTags.Blocks.PEAR_LEAVES).add(FLOWERING_PEAR_LEAVES.block(), PEAR_LEAVES.block());
        tag(YonderTags.Blocks.CAN_HANG_ON.PEAR).addTag(YonderTags.Blocks.PEAR_LEAVES);

        // vanilla tags
        tag(BlockTags.FLOWERS).add(FLOWERING_PEAR_LEAVES.block());
    }
}
