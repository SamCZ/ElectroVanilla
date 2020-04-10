package cz.hydracore.electrovanilla.listeners;

import cz.hydracore.utils.ItemBuilder;
import cz.hydracore.utils.RunnableHelper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PoopEvents implements Listener {

    private Map<Player, Long> lastPlayerToggle = new HashMap<>();

    public PoopEvents() {
        RunnableHelper.runTaskTimer(this::updatePoop, 20);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if(event.isSneaking()) {
            lastPlayerToggle.put(event.getPlayer(), System.currentTimeMillis());
        } else {
            lastPlayerToggle.remove(event.getPlayer());
        }
    }

    private void updatePoop() {
        long nowTime = System.currentTimeMillis();
        for(Map.Entry<Player, Long> entry : lastPlayerToggle.entrySet()) {
            Player player = entry.getKey();
            long lastTime = entry.getValue();

            if((nowTime - lastTime) > 5000) {
                entry.setValue(nowTime);

                ItemStack itemStack = new ItemBuilder(Material.COCOA_BEANS).setName(player.getName() + " Poop").build();
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_TURTLE_LAY_EGG, 1, 1);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_STEP, 1, 1);
            }
        }
    }
}
