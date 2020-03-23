package cz.hydracore.electrovanilla.machine.type;

import cz.hydracore.electrovanilla.Items;
import cz.hydracore.electrovanilla.machine.Machine;
import cz.hydracore.utils.Nbt;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Pulverizer extends Machine {

    public Pulverizer() {
        super("Pulverizer");
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

            if(!isPulverizedable(itemStack))
                continue;

            if(getBattery() == 0)
                continue;

            int pulverizedStatus = Nbt.getNbt_Int(itemStack, "crushed", 0);
            pulverizedStatus++;
            this.recharge(-1);

            if(pulverizedStatus >= 10) {
                if(itemStack.getAmount() > 1) {
                    itemStack = Nbt.setNbt_Int(itemStack, "crushed", 0);
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    inventory.setItem(i, itemStack);
                    pulverize(itemStack);
                } else {
                    inventory.setItem(i, null);
                    pulverize(itemStack);
                }
                break;
            }

            itemStack = Nbt.setNbt_Int(itemStack, "crushed", pulverizedStatus);
            inventory.setItem(i, itemStack);

            break;
        }
    }

    private void pulverize(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case IRON_ORE:
                ItemStack dust = Items.IRON_DUST.clone();
                dust.setAmount(2);
                getInventory().addItem(dust);
                break;
        }
    }

    private boolean isPulverizedable(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case IRON_ORE:
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
        return Items.PULVERIZER;
    }
}
