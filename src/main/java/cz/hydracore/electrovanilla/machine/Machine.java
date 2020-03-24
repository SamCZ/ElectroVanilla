package cz.hydracore.electrovanilla.machine;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class Machine implements InventoryHolder {

    private Inventory inventory;
    private int battery;

    public Machine(String name) {
        this.inventory = Bukkit.createInventory(this, 9 * 6, name);
    }

    public abstract void update();

    public abstract boolean canOutputPower();

    public abstract ItemStack getType();

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = Math.max(0, Math.min(100, battery));
    }

    public void recharge(int amount) {
        setBattery(getBattery() + amount);
    }

    public boolean isFull() {
        return this.battery >= 100;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
