package momo.dev.yonder.common.datagen;

import momo.dev.yonder.Yonder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class YonderItemModelProvider extends ItemModelProvider {
    public YonderItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Yonder.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //withExistingParent(DCoreBlocks.FLINT_PEBBLES.getId().toString(), mcLoc("item/generated")).texture("layer0", "nomansland:pebbles");
    }
}
