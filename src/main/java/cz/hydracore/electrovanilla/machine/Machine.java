package cz.hydracore.electrovanilla.machine;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.model.AbstractModel;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class Machine extends ElectricModelComponent implements InventoryHolder {

    public Machine(EItem eItem) {
        super(eItem);
    }

    @Override
    public void dropItemsOnDestroy(@NotNull World world, @NotNull Location location) {

    }

    @Override
    public Class<? extends AbstractModel> getModelClass() {
        return null;
    }

    @Override
    public void onTick() {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
