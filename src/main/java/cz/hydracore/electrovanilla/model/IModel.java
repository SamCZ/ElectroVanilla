package cz.hydracore.electrovanilla.model;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface IModel {

    void spawn(@NotNull Location location);

    void destroy();

    void setAttribute(int attributeId, boolean flag);

}
