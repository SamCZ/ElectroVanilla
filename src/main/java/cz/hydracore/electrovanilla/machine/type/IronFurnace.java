package cz.hydracore.electrovanilla.machine.type;

import cz.hydracore.electrovanilla.Items;
import cz.hydracore.electrovanilla.machine.Machine;
import cz.hydracore.utils.Nbt;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class IronFurnace extends Machine {

    public IronFurnace() {
        super("IronFurnace");
    }

    @Override
    public void update() {
        Inventory inventory = getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null)
                continue;

            if(itemStack.getType() == Material.COAL && getBattery() == 0) {
                setBattery(100);
                if(itemStack.getAmount() > 1) {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                } else {
                    inventory.setItem(i, null);
                }
                continue;
            }

            if(!isSmeltable(itemStack))
                continue;

            if(getBattery() == 0)
                continue;

            int pulverizedStatus = Nbt.getNbt_Int(itemStack, "smelted", 0);
            pulverizedStatus++;
            this.recharge(-1);

            if(pulverizedStatus >= 10) {
                if(itemStack.getAmount() > 1) {
                    itemStack = Nbt.setNbt_Int(itemStack, "smelted", 0);
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    inventory.setItem(i, itemStack);
                    smelt(itemStack);
                } else {
                    inventory.setItem(i, null);
                    smelt(itemStack);
                }
                break;
            }

            itemStack = Nbt.setNbt_Int(itemStack, "smelted", pulverizedStatus);
            inventory.setItem(i, itemStack);

            break;
        }
    }

    private void smelt(ItemStack itemStack) {
        switch (Nbt.getNbt_Int(itemStack, "ITEM_ID", -1)) {
            case 0:
                getInventory().addItem(new ItemStack(Material.IRON_INGOT));
        }
    }

    private boolean isSmeltable(ItemStack itemStack) {
        switch (Nbt.getNbt_Int(itemStack, "ITEM_ID", -1)) {
            case 0:
                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean canOutputPower() {
        return false;
    }

    @Override
    public ItemStack getType() {
        return Items.IRON_FURNACE;
    }
}
