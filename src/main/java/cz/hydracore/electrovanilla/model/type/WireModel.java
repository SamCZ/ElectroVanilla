package cz.hydracore.electrovanilla.model.type;

import cz.hydracore.electrovanilla.model.AbstractModel;
import cz.hydracore.utils.ArmorStandHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class WireModel extends AbstractModel {

    @Override
    protected void createModel(List<ArmorStand> models, int attributeId, @NotNull Location location) {
        if(attributeId > BASE_MODEL_ATTR_ID) {
            return;
        }

        BlockFace face = BlockFace.values()[attributeId];

        Random random = new Random();

        float rr = random.nextInt(10) / 900.0f;

        models.add(ArmorStandHelper.spawnWithHead(location.add(face.getModX() * 0.3 + rr, face.getModY() * 0.3 + rr, face.getModZ() * 0.3 + rr), true, 0, 0, new ItemStack(Material.IRON_BLOCK)));
    }
}
