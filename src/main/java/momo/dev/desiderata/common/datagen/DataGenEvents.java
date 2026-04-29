package momo.dev.desiderata.common.datagen;

import momo.dev.desiderata.DCore;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = DCore.MODID)
public class DataGenEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        // models
        generator.addProvider(event.includeClient(), new DCoreBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new DCoreItemModelProvider(output, existingFileHelper));
        // lang
        generator.addProvider(event.includeClient(), new DCoreLanguageProvider(output));
    }
}
