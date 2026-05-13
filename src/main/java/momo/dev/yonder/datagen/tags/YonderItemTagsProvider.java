package momo.dev.yonder.datagen.tags;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.registry.YonderTags;
import net.hecco.bountifulfares.definition.block.custom.FruitLogBlock;
import net.hecco.bountifulfares.definition.block.custom.StrippedFruitLogBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static momo.dev.yonder.common.registry.YonderBlocks.*;

public class YonderItemTagsProvider extends ItemTagsProvider {
    public YonderItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Yonder.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        for (BlockDefinition<?> def : BLOCK_DEFINITIONS) {
            if (def.get() instanceof SaplingBlock) tag(ItemTags.SAPLINGS).add(def.item());
            if (def.get() instanceof LeavesBlock) tag(ItemTags.LEAVES).add(def.item());
            if (def.get() instanceof FruitLogBlock) tag(ItemTags.LOGS_THAT_BURN).add(def.item());
            if (def.get() instanceof StrippedFruitLogBlock) tag(ItemTags.LOGS_THAT_BURN).add(def.item());
        }

        copy(YonderTags.Blocks.ASPEN_LOGS, YonderTags.Items.ASPEN_LOGS);
        copy(YonderTags.Blocks.BIRCH_LOGS, YonderTags.Items.BIRCH_LOGS);
        copy(YonderTags.Blocks.PEAR_LOGS, YonderTags.Items.PEAR_LOGS);
        copy(YonderTags.Blocks.PEAR_LEAVES, YonderTags.Items.PEAR_LEAVES);

        // vanilla tags
        tag(ItemTags.FLOWERS).add(FLOWERING_PEAR_LEAVES.item());
    }
}
