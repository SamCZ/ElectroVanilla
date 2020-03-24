package cz.hydracore.electrovanilla.item;

import cz.hydracore.electrovanilla.machine.IElectricComponentInstanceCreator;
import cz.hydracore.utils.Nbt;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EItem {

    private int id;
    private ItemStack itemStack;
    private IElectricComponentInstanceCreator electricComponentInstanceCreator;
    private Recipe recipe;

    public EItem(int id, @NotNull ItemStack itemStack, @Nullable IElectricComponentInstanceCreator electricComponentInstanceCreator, @Nullable Recipe recipe) {
        this.id = id;
        this.itemStack = Nbt.setNbt_Int(itemStack, "ITEM_ID", id);
        this.electricComponentInstanceCreator = electricComponentInstanceCreator;
        this.recipe = recipe;
    }

    public EItem(int id, @NotNull ItemStack itemStack, @Nullable IElectricComponentInstanceCreator electricComponentInstanceCreator) {
        this(id, itemStack, electricComponentInstanceCreator, null);
    }

    public EItem(int id, @NotNull ItemStack itemStack, @Nullable Recipe recipe) {
        this(id, itemStack, null, recipe);
    }

    public EItem(int id, @NotNull ItemStack itemStack) {
        this(id, itemStack, null, null);
    }

    public int getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public IElectricComponentInstanceCreator getElectricComponentInstanceCreator() {
        return electricComponentInstanceCreator;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
