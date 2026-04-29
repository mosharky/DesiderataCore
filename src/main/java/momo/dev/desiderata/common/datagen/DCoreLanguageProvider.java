package momo.dev.desiderata.common.datagen;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import momo.dev.desiderata.DCore;
import momo.dev.desiderata.common.registry.DCoreBlocks;
import momo.dev.desiderata.common.registry.DCoreItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class DCoreLanguageProvider extends LanguageProvider {
    public DCoreLanguageProvider(PackOutput output) {
        super(output, DCore.MODID, "en_us");
    }


    @Override
    protected void addTranslations() {
        for (BlockDefinition<?> definition : DCoreBlocks.BLOCK_DEFINITIONS) {
            if (!definition.hasCustomLang()) {
                add(definition.langKey(), definition.langName());
            }
        }

        for (ItemDefinition<?> definition : DCoreItems.ITEM_DEFINITIONS) {
            if (!definition.hasCustomLang() && !definition.isBlockItem()) {
                add(definition.langKey(), definition.langName());
            }
        }
    }
}
