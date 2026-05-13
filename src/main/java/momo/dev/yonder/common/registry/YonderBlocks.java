package momo.dev.yonder.common.registry;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.BlockProperties;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.block.StrikeablePickupBlock;
import momo.dev.yonder.common.registry.block.HangingPearBlock;
import momo.dev.yonder.common.registry.worldgen.YonderTreeGrowers;
import net.hecco.bountifulfares.definition.block.custom.FruitLeavesBlock;
import net.hecco.bountifulfares.definition.block.custom.FruitLogBlock;
import net.hecco.bountifulfares.definition.block.custom.StrippedFruitLogBlock;
import net.hecco.bountifulfares.registry.content.BFBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class YonderBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Yonder.MODID);
    public static List<BlockDefinition<?>> BLOCK_DEFINITIONS = new ArrayList<>();

    // Pebbles
    public static final BlockDefinition<StrikeablePickupBlock> FLINT_PEBBLES = register("flint_pebbles", () -> new StrikeablePickupBlock(() -> NMLBlocks.PEBBLES.stack(), ofFullCopy(NMLBlocks.PEBBLES.get())));

    // Dead
    public static final BlockDefinition<LeavesBlock> DEAD_LEAVES = register("dead_leaves", () -> new LeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating((s, g, p) -> false)));
    // Aspen
    public static final BlockDefinition<FruitLogBlock> ASPEN_LOG = register("aspen_log", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_LOG.get())));
    public static final BlockDefinition<FruitLogBlock> ASPEN_WOOD = register("aspen_wood", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_WOOD.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_ASPEN_LOG = register("stripped_aspen_log", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_LOG.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_ASPEN_WOOD = register("stripped_aspen_wood", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_WOOD.get())));
    public static final BlockDefinition<LeavesBlock> ASPEN_LEAVES = register("aspen_leaves", () -> new LeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating((s, g, p) -> false)));
    public static final BlockDefinition<SaplingBlock> ASPEN_SAPLING = register("aspen_sapling", () -> new SaplingBlock(YonderTreeGrowers.ASPEN, ofFullCopy(Blocks.BIRCH_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_ASPEN_SAPLING = registerNoItem("potted_aspen_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ASPEN_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(ASPEN_SAPLING));
    // Birch
    public static final BlockDefinition<FruitLogBlock> BIRCH_LOG = register("birch_log", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_LOG.get())));
    public static final BlockDefinition<FruitLogBlock> BIRCH_WOOD = register("birch_wood", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_WOOD.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_BIRCH_LOG = register("stripped_birch_log", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_LOG.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_BIRCH_WOOD = register("stripped_birch_wood", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_WOOD.get())));
    public static final BlockDefinition<LeavesBlock> BIRCH_LEAVES = register("birch_leaves", () -> new LeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).isViewBlocking((s, g, p) -> false).isSuffocating((s, g, p) -> false)));
    public static final BlockDefinition<SaplingBlock> BIRCH_SAPLING = register("birch_sapling", () -> new SaplingBlock(YonderTreeGrowers.BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING)), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_BIRCH_SAPLING = registerNoItem("potted_birch_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), BIRCH_SAPLING,
                    ofFullCopy(Blocks.POTTED_BIRCH_SAPLING).noOcclusion()), BlockProperties.flowerPot(BIRCH_SAPLING));
    // Pear
    public static final BlockDefinition<FruitLogBlock> PEAR_LOG = register("pear_log", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_LOG.get())));
    public static final BlockDefinition<FruitLogBlock> PEAR_WOOD = register("pear_wood", () -> new FruitLogBlock(ofFullCopy(BFBlocks.APPLE_WOOD.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_PEAR_LOG = register("stripped_pear_log", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_LOG.get())));
    public static final BlockDefinition<StrippedFruitLogBlock> STRIPPED_PEAR_WOOD = register("stripped_pear_wood", () -> new StrippedFruitLogBlock(ofFullCopy(BFBlocks.STRIPPED_APPLE_WOOD.get())));
    public static final BlockDefinition<HangingPearBlock> HANGING_PEAR = registerNoItem("hanging_pear", () -> new HangingPearBlock(ofFullCopy(BFBlocks.HANGING_APPLE.get())));
    public static final BlockDefinition<LeavesBlock> PEAR_LEAVES = register("pear_leaves", () -> new FruitLeavesBlock(HANGING_PEAR.get().defaultBlockState(), ofFullCopy(BFBlocks.APPLE_LEAVES.get())));
    public static final BlockDefinition<LeavesBlock> FLOWERING_PEAR_LEAVES = register("flowering_pear_leaves", () -> new FruitLeavesBlock(HANGING_PEAR.get().defaultBlockState(), ofFullCopy(BFBlocks.FLOWERING_APPLE_LEAVES.get())));
    public static final BlockDefinition<SaplingBlock> PEAR_SAPLING = register("pear_sapling", () -> new SaplingBlock(YonderTreeGrowers.PEAR, ofFullCopy(BFBlocks.APPLE_SAPLING.get())), BlockProperties.sapling());
    public static final BlockDefinition<FlowerPotBlock> POTTED_PEAR_SAPLING = registerNoItem("potted_pear_sapling",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), PEAR_SAPLING,
                    ofFullCopy(Blocks.POTTED_OAK_SAPLING).noOcclusion()), BlockProperties.flowerPot(PEAR_SAPLING));

    // Helpers (from NMLBlocks)
    public static <T extends Block> BlockDefinition<T> registerNoItem(String name, Supplier<T> block, BlockProperties properties) {
        DeferredBlock<T> deferred = BLOCKS.register(name, block);
        BlockDefinition<T> definition = BlockDefinition.fromHolder(deferred, properties);
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    public static <T extends Block> BlockDefinition<T> registerNoItem(String name, Supplier<T> block) {
        return registerNoItem(name, block, BlockProperties.custom(false));
    }

    public static <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block, BlockProperties properties) {
        BlockDefinition<T> definition = registerNoItem(name, block, properties);
        YonderItems.register(name, () -> new BlockItem(definition.get(), new Item.Properties()));
        return definition;
    }

    public static <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block) {
        return register(name, block, BlockProperties.custom(false));
    }
}
