package cz.hydracore.electrovanilla.model;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractModel implements IModel {

    public static final int BASE_MODEL_ATTR_ID = BlockFace.SELF.ordinal();

    private Map<Integer, List<ArmorStand>> modelMap = new HashMap<>();
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
        this.modelMap.values().forEach(armorStands -> armorStands.forEach(ArmorStand::remove));

        this.modelMap.clear();
    }

    @Override
    public void setAttribute(int attributeId, boolean flag) {
        assert baseLocation != null;

        boolean spawned = this.modelMap.containsKey(attributeId);

        if(spawned && !flag) {
            this.modelMap.get(attributeId).forEach(ArmorStand::remove);
            this.modelMap.remove(attributeId);
        } else if(!spawned && flag) {
            List<ArmorStand> list = new ArrayList<>();
            createModel(list, attributeId, this.baseLocation.clone());
            if(list.size() > 0) {
                this.modelMap.put(attributeId, list);
            }
        }
    }

    protected abstract void createModel(List<ArmorStand> models, int attributeId, @NotNull Location location);
}
