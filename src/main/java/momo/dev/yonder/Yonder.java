package momo.dev.yonder;

import momo.dev.yonder.common.registry.YonderBlocks;
import momo.dev.yonder.common.registry.YonderFlammables;
import momo.dev.yonder.common.registry.YonderItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Yonder.MODID)
public class Yonder {
    public static final String MODID = "yonder";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Yonder(IEventBus bus, ModContainer container) {
        bus.addListener(this::commonSetup);

        YonderBlocks.BLOCKS.register(bus);
        YonderItems.ITEMS.register(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            YonderFlammables.register();
        });
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
