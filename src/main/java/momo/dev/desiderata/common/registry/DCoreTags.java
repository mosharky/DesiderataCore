package momo.dev.desiderata.common.registry;

import momo.dev.desiderata.DCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class DCoreTags {
    // For BountifulFares fruit trees
    public static class CanHangOn {
        public static final TagKey<Block> HOARY_APPLE = blockTag("can_hang_on/hoary_apple");
        public static final TagKey<Block> WALNUT = blockTag("can_hang_on/walnut");
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, DCore.loc(name));
    }

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, DCore.loc(name));
    }

    private static TagKey<Biome> biomeTag(String name) {
        return TagKey.create(Registries.BIOME, DCore.loc(name));
    }

    private static TagKey<EntityType<?>> entityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, DCore.loc(name));
    }

    private static TagKey<DamageType> createDamageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, DCore.loc(name));
    }
}
