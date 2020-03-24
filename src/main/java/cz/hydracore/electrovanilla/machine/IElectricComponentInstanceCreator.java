package cz.hydracore.electrovanilla.machine;

import cz.hydracore.electrovanilla.ElectroVanilla;
import cz.hydracore.electrovanilla.electric.IElectricComponent;
import cz.hydracore.electrovanilla.item.EItem;
import org.jetbrains.annotations.NotNull;

public interface IElectricComponentInstanceCreator {

    IElectricComponent createNewInstance(@NotNull ElectroVanilla electroVanilla, @NotNull EItem eItem);

}
