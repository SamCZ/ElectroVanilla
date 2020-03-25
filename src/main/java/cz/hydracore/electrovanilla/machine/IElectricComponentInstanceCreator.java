package cz.hydracore.electrovanilla.machine;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import org.jetbrains.annotations.NotNull;

public interface IElectricComponentInstanceCreator {

    ElectricModelComponent createNewInstance(@NotNull EItem eItem);

}
