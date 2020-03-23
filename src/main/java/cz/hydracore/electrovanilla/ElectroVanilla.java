package cz.hydracore.electrovanilla;

import com.mojang.datafixers.util.Pair;
import cz.hydracore.electrovanilla.machine.Machine;
import cz.hydracore.electrovanilla.machine.type.IronFurnace;
import cz.hydracore.electrovanilla.machine.type.Pulverizer;
import cz.hydracore.utils.Nbt;
import cz.hydracore.utils.RunnableHelper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ElectroVanilla extends JavaPlugin implements Listener {

    private static ElectroVanilla Instance;

    private List<Pair<Location, Machine>> machines = new ArrayList<>();

    @Override
    public void onEnable() {
        Instance = this;

        /*{
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "wire"), Items.WIRE);
            recipe.shape("   ", "WWW", "   ");
            recipe.setIngredient('W', Material.IRON_INGOT);
            Bukkit.addRecipe(recipe);
        }*/

        {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "pulverizer"), Items.PULVERIZER);
            recipe.shape(" B ", " F ", " B ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);
        }

        {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "iron_furnace"), Items.IRON_FURNACE);
            recipe.shape(" B ", " F ", " F ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);
        }

        getServer().getPluginManager().registerEvents(this, this);

        RunnableHelper.runTaskTimer(this::updateMachines, 10);
    }

    private Machine createMachine(int id) {
        switch (id) {
            case 2:
                return new Pulverizer();
            case 3:
                return new IronFurnace();
            default:
                return null;
        }
    }

    private void updateMachines() {
        for(Pair<Location, Machine> machinePair : this.machines) {
            machinePair.getSecond().update();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND)
            return;

        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case PHYSICAL:
            case RIGHT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                return;
        }

        Player player = event.getPlayer();

        Block block = event.getClickedBlock();

        if(block == null) {
            return;
        }
        Location location = block.getLocation();

        Iterator<Pair<Location, Machine>> iterator = this.machines.iterator();

        while (iterator.hasNext()) {
            Pair<Location, Machine> pair = iterator.next();
            Location mLocation = pair.getFirst();

            Machine machine = pair.getSecond();

            if(equalsBlockLocation(location, mLocation)) {
                event.setCancelled(true);

                player.openInventory(machine.getInventory());

                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ItemStack itemStack = event.getItemInHand();

        int itemId = Nbt.getNbt_Int(itemStack, "ITEM_ID", -1);

        if(itemId == -1) {
            return;
        }

        Machine machine = createMachine(itemId);

        if(machine == null) {
            return;
        }

        this.machines.add(new Pair<>(block.getLocation(), machine));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        Iterator<Pair<Location, Machine>> iterator = this.machines.iterator();

        while (iterator.hasNext()) {
            Pair<Location, Machine> pair = iterator.next();
            Location mLocation = pair.getFirst();

            Machine machine = pair.getSecond();

            if(equalsBlockLocation(location, mLocation)) {
                iterator.remove();

                mLocation.getWorld().dropItemNaturally(mLocation, machine.getType().clone());
                event.setDropItems(false);

                for(ItemStack itemStack : machine.getInventory().getContents()) {
                    if(itemStack == null)
                        continue;
                    mLocation.getWorld().dropItemNaturally(mLocation, itemStack);
                }
                break;
            }
        }
    }

    public static boolean equalsBlockLocation(Location loc1, Location loc2) {
        return loc1.getWorld() == loc2.getWorld() && loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    @Override
    public void onDisable() {
        
    }

    public static ElectroVanilla getInstance() {
        return Instance;
    }
}
