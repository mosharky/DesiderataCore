package momo.dev.desiderata.common.registry;

import com.farcr.nomansland.common.definitions.ItemDefinition;
import com.google.common.collect.Sets;
import momo.dev.desiderata.DCore;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;

public class DCoreItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DCore.MODID);
    public static List<ItemDefinition<?>> ITEM_DEFINITIONS = new ArrayList<>();
    public static LinkedHashSet<ItemDefinition<?>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Helpers (from NMLItems)
    public static <T extends Item> ItemDefinition<T> registerWithoutTab(String name, Supplier<T> item, boolean customLang) {
        DeferredItem<T> deferred = ITEMS.register(name, item);
        ItemDefinition<T> definition = ItemDefinition.fromHolder(deferred, customLang);
        ITEM_DEFINITIONS.add(definition);
        return definition;
    }

    public static <T extends Item> ItemDefinition<T> register(String name, Supplier<T> item, boolean customLang) {
        ItemDefinition<T> definition = registerWithoutTab(name, item, customLang);
        CREATIVE_TAB_ITEMS.add(definition);
        return definition;
    }

    public static <T extends Item> ItemDefinition<T> registerWithoutTab(String name, Supplier<T> item) {
        return registerWithoutTab(name, item, false);
    }

    public static <T extends Item> ItemDefinition<T> register(String name, Supplier<T> item) {
        return register(name, item, false);
    }
}
