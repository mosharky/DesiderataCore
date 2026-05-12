package momo.dev.yonder;

import momo.dev.yonder.common.registry.YonderBlocks;
import momo.dev.yonder.common.registry.YonderItems;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Yonder.MODID)
public class Yonder {
    public static final String MODID = "yonder";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Yonder(IEventBus bus, ModContainer container) {
        // NeoForge.EVENT_BUS.register(this);
        YonderBlocks.BLOCKS.register(bus);
        YonderItems.ITEMS.register(bus);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
