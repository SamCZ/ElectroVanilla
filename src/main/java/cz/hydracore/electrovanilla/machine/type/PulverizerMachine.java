package cz.hydracore.electrovanilla.machine.type;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.model.AbstractModel;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class PulverizerMachine extends ElectricModelComponent {

    public PulverizerMachine(EItem eItem) {
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
}
