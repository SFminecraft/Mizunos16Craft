package sf.mizuno.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import sf.mizuno.Mizunos16CraftMod;
import sf.mizuno.item.ModBlockItem;

import java.util.Arrays;
import java.util.function.Supplier;

public enum ItemsRegistry {
    OAK_LOG("oak_log", () -> new ModBlockItem(BlocksRegistry.OAK_LOG.get())),
    OAK_PLANKS("oak_planks", () -> new ModBlockItem(BlocksRegistry.OAK_PLANKS.get())),
    SPRUCE_PLANKS("spruce_planks", () -> new ModBlockItem(BlocksRegistry.SPRUCE_PLANKS.get())),
    BIRCH_PLANKS("birch_planks", () -> new ModBlockItem(BlocksRegistry.BIRCH_PLANKS.get()));

    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private final Integer burnTime;
    private Item item;

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier) {
        this(pathName, itemSupplier, null);
    }

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier, Integer burnTime) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.burnTime = burnTime;
    }

    public static void registerAll() {
        for (ItemsRegistry value : values()) {
            Registry.register(Registries.ITEM, new Identifier(Mizunos16CraftMod.MOD_ID, value.pathName), value.get());
            if (value.burnTime != null && value.burnTime > 0) {
                FuelRegistry.INSTANCE.add(value.get(), value.burnTime);
            }
        }
        ItemGroupEvents.modifyEntriesEvent(Mizunos16CraftMod.ITEM_GROUP).register(entries -> entries.addAll(
                Arrays.stream(values()).map(item -> item.get().getDefaultStack()).toList()));
    }

    public Item get() {
        if (item == null) {
            item = itemSupplier.get();
        }
        return item;
    }

    public String getId() {
        return Registries.ITEM.getId(get()).toString();
    }

    }
