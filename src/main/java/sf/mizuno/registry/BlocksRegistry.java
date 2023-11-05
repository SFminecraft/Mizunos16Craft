package sf.mizuno.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import sf.mizuno.Mizunos16CraftMod;

import java.util.function.Supplier;

public enum BlocksRegistry {

    OAK_LOG("oak_log", () -> new Block(FabricBlockSettings.copy(Blocks.OAK_LOG))),
    OAK_PLANKS("oak_planks", () -> new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).hardness(2.f).resistance(3.f).sounds(BlockSoundGroup.WOOD))),
    SPRUCE_PLANKS("spruce_planks", () -> new Block(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS).hardness(2.f).resistance(3.f))),
    BIRCH_PLANKS("birch_planks", () -> new Block(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS)));

    private static FlammableBlockRegistry.Entry flammable(int burnChance, @SuppressWarnings("SameParameterValue") int spreadChance) {
        return new FlammableBlockRegistry.Entry(burnChance, spreadChance);
    }

    private static boolean isValidFlammableEntry(FlammableBlockRegistry.Entry flammableRate) {
        return flammableRate != null && flammableRate.getBurnChance() > 0 && flammableRate.getSpreadChance() > 0;
    }

    private final String pathName;
    private final Supplier<Block> blockSupplier;
    private final FlammableBlockRegistry.Entry flammableRate;
    private final boolean isCutout;
    private Block block;

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier) {
        this(pathName, blockSupplier, false, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
        this(pathName, blockSupplier, isCutout, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout, FlammableBlockRegistry.Entry flammableRate) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.flammableRate = flammableRate;
        this.isCutout = isCutout;
    }

    public static void registerAll() {
        for (BlocksRegistry value : values()) {
            Block block = value.get();
            Registry.register(Registries.BLOCK, new Identifier(Mizunos16CraftMod.MOD_ID, value.pathName), block);
            if (isValidFlammableEntry(value.flammableRate)) {
                FlammableBlockRegistry.getDefaultInstance().add(block, value.flammableRate);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (BlocksRegistry value : values()) {
            if (value.isCutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutout());
            }
        }
    }

    public Block get() {
        if (block == null) {
            block = blockSupplier.get();
        }

        return block;
    }

    public String getId() {
        return Registries.BLOCK.getId(get()).toString();
    }

}
