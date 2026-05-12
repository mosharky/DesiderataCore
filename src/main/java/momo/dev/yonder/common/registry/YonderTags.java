package momo.dev.yonder.common.registry;

import momo.dev.yonder.Yonder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class YonderTags {
    public static class Blocks {
        // For BountifulFares hanging fruits
        public static class CanHangOn {
            public static final TagKey<Block> APPLE = blockTag("can_hang_on/apple");
            public static final TagKey<Block> GOLDEN_APPLE = blockTag("can_hang_on/golden_apple");
            public static final TagKey<Block> HOARY_APPLE = blockTag("can_hang_on/hoary_apple");
            public static final TagKey<Block> LEMON = blockTag("can_hang_on/lemon");
            public static final TagKey<Block> ORANGE = blockTag("can_hang_on/orange");
            public static final TagKey<Block> PLUM = blockTag("can_hang_on/plum");
            public static final TagKey<Block> WALNUT = blockTag("can_hang_on/walnut");
            public static final TagKey<Block> WITHERED_GOLDEN_APPLE = blockTag("can_hang_on/withered_golden_apple");
        }
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, Yonder.loc(name));
    }

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, Yonder.loc(name));
    }

    private static TagKey<Biome> biomeTag(String name) {
        return TagKey.create(Registries.BIOME, Yonder.loc(name));
    }

    private static TagKey<EntityType<?>> entityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Yonder.loc(name));
    }

    private static TagKey<DamageType> createDamageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, Yonder.loc(name));
    }
}
