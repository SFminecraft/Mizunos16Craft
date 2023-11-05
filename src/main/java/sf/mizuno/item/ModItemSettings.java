package sf.mizuno.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ModItemSettings extends FabricItemSettings {

    public static FabricItemSettings base() {
        return new ModItemSettings();
    }

    public static FabricItemSettings noStack() {
        return new ModItemSettings().maxCount(1);
    }

    public ModItemSettings() {
        super();
    }
}
