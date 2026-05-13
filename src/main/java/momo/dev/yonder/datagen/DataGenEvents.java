package momo.dev.yonder.datagen;

import momo.dev.yonder.Yonder;
import momo.dev.yonder.datagen.tags.YonderBlockTagsProvider;
import momo.dev.yonder.datagen.tags.YonderItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Yonder.MODID)
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
        generator.addProvider(client, new YonderBlockStateProvider(output, existingFileHelper));
        generator.addProvider(client, new YonderItemModelProvider(output, existingFileHelper));
        // lang
        generator.addProvider(client, new YonderLanguageProvider(output));
        // tags
        BlockTagsProvider blockTagsProvider = generator.addProvider(server, new YonderBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(server, new YonderItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }
}
