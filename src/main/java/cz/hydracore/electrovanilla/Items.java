package cz.hydracore.electrovanilla;

import cz.hydracore.utils.ItemBuilder;
import cz.hydracore.utils.Nbt;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Items {

    private static Map<Integer, ItemStack> itemStackMap = new HashMap<>();

    public static ItemStack IRON_DUST = register(new ItemBuilder(Material.COCOA_BEANS).setName("Iron dust").setNbt_Int("ITEM_ID", 0).build());
    public static ItemStack WIRE = register(new ItemBuilder(Material.IRON_BLOCK).setName("Wire").setNbt_Int("ITEM_ID", 1).build());
    public static ItemStack PULVERIZER = register(new ItemBuilder(Material.SMITHING_TABLE).setName("Pulverizer").setNbt_Int("ITEM_ID", 2).build());
    public static ItemStack IRON_FURNACE = register(new ItemBuilder(Material.FLETCHING_TABLE).setName("Iron furnace").setNbt_Int("ITEM_ID", 3).build());

    private static ItemStack register(ItemStack itemStack) {
        itemStackMap.put(Nbt.getNbt_Int(itemStack, "ITEM_ID", -1), itemStack);
        return itemStack;
    }

    public static ItemStack getById(int id) {
        return itemStackMap.getOrDefault(id, null);
    }

}
