package cz.hydracore.electrovanilla.model;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModel implements IModel {

    public static final int BASE_MODEL_ATTR_ID = 0;

    private Map<Integer, ArmorStand> modelMap = new HashMap<>();
    private Location baseLocation;

    protected AbstractModel() {

    }

    @Override
    public void spawn(@NotNull Location location) {
        this.baseLocation = location;
        setAttribute(BASE_MODEL_ATTR_ID, true);
    }

    @Override
    public void destroy() {
        for(ArmorStand armorStand : this.modelMap.values()) {
            armorStand.remove();
        }

        this.modelMap.clear();
    }

    @Override
    public void setAttribute(int attributeId, boolean flag) {
        assert baseLocation != null;

        boolean spawned = this.modelMap.containsKey(attributeId);

        if(spawned && !flag) {
            this.modelMap.get(attributeId).remove();
            this.modelMap.remove(attributeId);
        } else if(!spawned && flag) {
            this.modelMap.put(attributeId, createModel(attributeId, this.baseLocation));
        }
    }

    protected abstract ArmorStand createModel(int attributeId, @NotNull Location location);
}
