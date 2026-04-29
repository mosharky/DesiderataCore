package momo.dev.desiderata;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(DCore.MODID)
public class DCore {
    public static final String MODID = "desiderata";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DCore(IEventBus bus, ModContainer container) {
        // NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
