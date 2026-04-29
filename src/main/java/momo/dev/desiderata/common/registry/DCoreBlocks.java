package momo.dev.desiderata.common.registry;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.BlockProperties;
import com.farcr.nomansland.common.registry.blocks.NMLBlocks;
import com.farcr.nomansland.common.registry.items.NMLItems;
import momo.dev.desiderata.DCore;
import momo.dev.desiderata.common.block.StrikeablePickupBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class DCoreBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DCore.MODID);
    public static List<BlockDefinition<?>> BLOCK_DEFINITIONS = new ArrayList<>();

    public static final BlockDefinition<StrikeablePickupBlock> FLINT_PEBBLES = register("flint_pebbles",
            () -> new StrikeablePickupBlock(() -> NMLBlocks.PEBBLES.stack(), of().mapColor(MapColor.STONE).noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY)));

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
        DCoreItems.register(name, () -> new BlockItem(definition.get(), new Item.Properties()));
        return definition;
    }

    public static <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block) {
        return register(name, block, BlockProperties.custom(false));
    }
}
