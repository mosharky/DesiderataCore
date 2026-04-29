package momo.dev.desiderata.common.datagen;

import momo.dev.desiderata.DCore;
import momo.dev.desiderata.common.registry.DCoreBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DCoreItemModelProvider extends ItemModelProvider {
    public DCoreItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DCore.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //withExistingParent(DCoreBlocks.FLINT_PEBBLES.getId().toString(), mcLoc("item/generated")).texture("layer0", "nomansland:pebbles");
    }
}
