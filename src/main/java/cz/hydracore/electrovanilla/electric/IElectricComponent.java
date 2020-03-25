package cz.hydracore.electrovanilla.electric;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface IElectricComponent {

    void onTick();

    void onSpawned(@NotNull World world, @NotNull Location location);

    void onDestroyed(@NotNull World world, @NotNull Location location);

}
