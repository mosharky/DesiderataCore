package momo.dev.yonder.client;

import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.registry.YonderBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;


@EventBusSubscriber(modid = Yonder.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, index) -> FoliageColor.get(0.5D, 1),
                YonderBlocks.ASPEN_LEAVES.get(),
                YonderBlocks.BIRCH_LEAVES.get(),
                YonderBlocks.PEAR_LEAVES.get(),
                YonderBlocks.FLOWERING_PEAR_LEAVES.get()
        );
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : GrassColor.get(0.5D, 1),
                YonderBlocks.ASPEN_LEAVES.get(),
                YonderBlocks.BIRCH_LEAVES.get(),
                YonderBlocks.PEAR_LEAVES.get(),
                YonderBlocks.FLOWERING_PEAR_LEAVES.get()
                // YonderBlocks.ASPEN_SAPLING.get(),
                // YonderBlocks.BIRCH_SAPLING.get(),
                // YonderBlocks.PEAR_SAPLING.get()
        );
    }
}
