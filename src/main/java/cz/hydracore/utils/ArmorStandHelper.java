package cz.hydracore.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandHelper {

    public static ArmorStand spawnWithHead(@NotNull Location location, boolean small, float xRotation, float yRotation, @NotNull ItemStack headItem) {
        Location armorLocation = location.clone().add(0.5, 0, 0.5);
        armorLocation.setPitch(xRotation);
        armorLocation.setYaw(yRotation);
        ArmorStand armorStand = location.getWorld().spawn(armorLocation, ArmorStand.class);

        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.getEquipment().setHelmet(headItem);
        armorStand.setVisible(false);
        armorStand.setSmall(small);
        armorStand.setBasePlate(false);

        armorStand.setLeftLegPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));
        armorStand.setRightLegPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));
        armorStand.setHeadPose(new EulerAngle(-180 * (Math.PI / 180.0D), 0, 0));

        return armorStand;
    }

}
