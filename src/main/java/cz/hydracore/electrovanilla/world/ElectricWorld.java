package cz.hydracore.electrovanilla.world;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.machine.IElectricComponentInstanceCreator;
import cz.hydracore.utils.BlockLocation;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ElectricWorld {

    private Map<BlockLocation, ElectricBlockStorage> electricComponentsInstances = new HashMap<>();

    public ElectricBlockStorage spawn(@NotNull EItem eItem, @NotNull Location location) {
        BlockLocation blockLocation = new BlockLocation(location);

        System.out.println(electricComponentsInstances.containsKey(blockLocation));

        if(electricComponentsInstances.containsKey(blockLocation)) {
            return null;
        }

        IElectricComponentInstanceCreator electricComponentInstanceCreator = eItem.getElectricComponentInstanceCreator();

        if(electricComponentInstanceCreator == null) {
            return null;
        }

        ElectricModelComponent modelComponent = electricComponentInstanceCreator.createNewInstance(eItem);
        modelComponent.onSpawned(location.getWorld(), location.clone());

        ElectricBlockStorage storage = new ElectricBlockStorage(this, location.clone(), modelComponent);
        electricComponentsInstances.put(blockLocation, storage);
        modelComponent.update(storage);

        for (int i = 0; i < 6; i++) {
            BlockFace face = BlockFace.values()[i];

            ElectricBlockStorage relativeStorage = storage.getRelative(face);

            if(relativeStorage == null) continue;

            relativeStorage.getElectricModelComponent().update(relativeStorage);
        }

        return storage;
    }

    public boolean destroy(@NotNull Location location) {
        BlockLocation blockLocation = new BlockLocation(location);
        if(!electricComponentsInstances.containsKey(blockLocation)) {
            return false;
        }

        ElectricBlockStorage storage = electricComponentsInstances.get(blockLocation);
        storage.getElectricModelComponent().onDestroyed(storage.getLocation().getWorld(), storage.getLocation(), true);
        electricComponentsInstances.remove(blockLocation);

        for (int i = 0; i < 6; i++) {
            BlockFace face = BlockFace.values()[i];

            ElectricBlockStorage relativeStorage = storage.getRelative(face);

            if(relativeStorage == null) continue;

            relativeStorage.getElectricModelComponent().update(relativeStorage);
        }

        return true;
    }

    public ElectricBlockStorage getElectricComponent(@NotNull Location location) {
        return electricComponentsInstances.getOrDefault(new BlockLocation(location), null);
    }

    public void destroyAll() {
        for(ElectricBlockStorage storage : electricComponentsInstances.values()) {
            storage.getElectricModelComponent().onDestroyed(storage.getLocation().getWorld(), storage.getLocation(), false);
        }
    }

}
