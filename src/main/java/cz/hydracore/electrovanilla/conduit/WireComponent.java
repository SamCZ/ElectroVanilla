package cz.hydracore.electrovanilla.conduit;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.model.AbstractModel;
import cz.hydracore.electrovanilla.model.type.WireModel;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class WireComponent extends ElectricModelComponent {

    public WireComponent(EItem eItem) {
        super(eItem);
    }

    @Override
    public void dropItemsOnDestroy(@NotNull World world, @NotNull Location location) {

    }

    @Override
    public Class<? extends AbstractModel> getModelClass() {
        return WireModel.class;
    }

    @Override
    public void onTick() {

    }
}
