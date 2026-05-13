package momo.dev.yonder.datagen;

import com.farcr.nomansland.common.definitions.BlockDefinition;
import com.farcr.nomansland.common.definitions.ItemDefinition;
import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.registry.YonderBlocks;
import momo.dev.yonder.common.registry.YonderItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class YonderLanguageProvider extends LanguageProvider {
    public YonderLanguageProvider(PackOutput output) {
        super(output, Yonder.MODID, "en_us");
    }


    @Override
    protected void addTranslations() {
        for (BlockDefinition<?> definition : YonderBlocks.BLOCK_DEFINITIONS) {
            if (!definition.hasCustomLang()) {
                add(definition.langKey(), definition.langName());
            }
        }

        for (ItemDefinition<?> definition : YonderItems.ITEM_DEFINITIONS) {
            if (!definition.hasCustomLang() && !definition.isBlockItem()) {
                add(definition.langKey(), definition.langName());
            }
        }
    }
}
