package cz.hydracore.electrovanilla.machine.type;

import cz.hydracore.electrovanilla.Battery;
import cz.hydracore.electrovanilla.core.Output;
import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.model.AbstractModel;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class GeneratorMachine extends ElectricModelComponent {

    private Battery battery;

    public GeneratorMachine(EItem eItem) {
        super(eItem);

        this.battery = new Battery(500, 0);
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
    public Output getOutput() {
        return this.battery;
    }
}
