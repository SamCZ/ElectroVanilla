package cz.hydracore.electrovanilla.world;

import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class ElectricBlockStorage {

    private ElectricWorld electricWorld;
    private Location location;
    private ElectricModelComponent electricModelComponent;

    public ElectricBlockStorage(ElectricWorld electricWorld, Location location, ElectricModelComponent electricModelComponent) {
        this.electricWorld = electricWorld;
        this.location = location;
        this.electricModelComponent = electricModelComponent;
    }

    public ElectricWorld getElectricWorld() {
        return electricWorld;
    }

    public Location getLocation() {
        return location;
    }

    public ElectricModelComponent getElectricModelComponent() {
        return electricModelComponent;
    }

    public ElectricBlockStorage getRelative(BlockFace blockFace) {
        return this.electricWorld.getElectricComponent(this.location.getBlock().getRelative(blockFace).getLocation());
    }
}
