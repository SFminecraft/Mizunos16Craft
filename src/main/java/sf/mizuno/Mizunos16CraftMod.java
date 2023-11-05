package sf.mizuno;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import sf.mizuno.registry.BlocksRegistry;
import sf.mizuno.registry.ItemsRegistry;

public class Mizunos16CraftMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("Mizuno's 16 Craft");

	public static final String MOD_ID = "mizuno";


	public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "building_block"));

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.mizuno.building_block"))
				.icon(() -> new ItemStack(ItemsRegistry.OAK_PLANKS.get()))
				.build());

		BlocksRegistry.registerAll();
		ItemsRegistry.registerAll();

		LOGGER.info("Hello Fabric world!");
	}
}