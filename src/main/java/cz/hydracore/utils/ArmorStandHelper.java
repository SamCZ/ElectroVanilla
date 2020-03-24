package cz.hydracore.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandHelper {

    public static ArmorStand spawnWithHead(@NotNull World world, @NotNull Location location, float xRotation, float yRotation, @NotNull ItemStack headItem) {
        Location armorLocation = location.clone().add(0.5, -0.5f, 0.5);
        armorLocation.setPitch(xRotation);
        armorLocation.setYaw(yRotation);
        ArmorStand armorStand = world.spawn(armorLocation, ArmorStand.class);

        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.getEquipment().setHelmet(headItem);
        armorStand.setVisible(false);

        armorStand.setLeftLegPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));
        armorStand.setRightLegPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));
        armorStand.setHeadPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));

        return armorStand;
    }

}
