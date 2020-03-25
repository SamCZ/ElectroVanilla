package cz.hydracore.electrovanilla;

import com.mojang.datafixers.util.Pair;
import cz.hydracore.electrovanilla.conduit.WireComponent;
import cz.hydracore.electrovanilla.electric.IElectricComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.item.ItemManager;
import cz.hydracore.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ElectroVanilla extends JavaPlugin implements Listener {

    private static ElectroVanilla Instance;

    private ItemManager itemManager;

    private List<Pair<Location, IElectricComponent>> electricComponenetsInstances = new ArrayList<>();

    @Override
    public void onEnable() {
        Instance = this;

        itemManager = new ItemManager();

        itemManager.register(new EItem(0, new ItemBuilder(Material.COCOA_BEANS).setName("Iron dust").build()));
        itemManager.register(new EItem(4, new ItemBuilder(Material.BLAZE_POWDER).setName("Gold dust").build()));

        {
            ItemStack itemStack = new ItemBuilder(Material.BLACK_WOOL).setNbt_Int("ITEM_ID", 1).setName("Wire").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "wire"), itemStack);
            recipe.shape("   ", "WRW", "   ");
            recipe.setIngredient('W', Material.IRON_INGOT);
            recipe.setIngredient('R', Material.REDSTONE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(1, itemStack, (electroVanilla, eItem) -> new WireComponent(eItem), recipe));
        }

        {
            ItemStack itemStack = new ItemBuilder(Material.SMITHING_TABLE).setName("Pulverizer").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "pulverizer"), itemStack);
            recipe.shape(" B ", " F ", " B ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(2, itemStack, (electroVanilla, eItem) -> null, recipe));
        }

        {
            ItemStack itemStack = new ItemBuilder(Material.FLETCHING_TABLE).setName("Iron furnace").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "iron_furnace"), itemStack);
            recipe.shape(" B ", " F ", " F ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(3, itemStack, (electroVanilla, eItem) -> null, recipe));
        }

        getServer().getPluginManager().registerEvents(this, this);

        RunnableHelper.runTaskTimer(this::updateMachines, 10);
    }

    private void updateMachines() {
        /*for(Pair<Location, Machine> machinePair : this.machines) {
            machinePair.getSecond().update();
        }*/
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        /*if(event.getHand() != EquipmentSlot.HAND)
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
        }*/
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ItemStack itemStack = event.getItemInHand();

        int itemId = Nbt.getNbt_Int(itemStack, "ITEM_ID", -1);

        if(itemId == -1) {
            return;
        }

        event.setCancelled(true);

        System.out.println(itemId);

        EItem eItem = itemManager.getItemById(itemId);

        IElectricComponent electricComponent = eItem.getElectricComponentInstanceCreator().createNewInstance(this, eItem);
        electricComponent.onSpawned(block.getWorld(), block.getLocation());

        electricComponenetsInstances.add(new Pair<>(block.getLocation(), electricComponent));

        /*this.armorStandList.add(ArmorStandHelper.spawnWithHead(block.getWorld(), block.getLocation().clone().add(0, 0.5 - 0.25 , 0), 0, 180, PlayerHead.Clock.build()));
        this.armorStandList.add(ArmorStandHelper.spawnWithHead(block.getWorld(), block.getLocation().clone().add(0, -0.25, 0), 0, 0, new ItemStack(Material.BLAST_FURNACE)));

        if(true) return;

        EItem eItem = itemManager.getItemById(itemId);

        Machine machine = eItem.getElectricComponentInstanceCreator().createNewInstance(this, eItem);

        if(machine == null) {
            return;
        }

        this.machines.add(new Pair<>(block.getLocation(), machine));*/
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(event.getRightClicked() instanceof ArmorStand ? "ar" : "no");

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(event.getRightClicked() instanceof ArmorStand ? "ar" : "no");

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        /*Iterator<Pair<Location, Machine>> iterator = this.machines.iterator();

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
        }*/
    }

    public static boolean equalsBlockLocation(Location loc1, Location loc2) {
        return loc1.getWorld() == loc2.getWorld() && loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    @Override
    public void onDisable() {
        for(Pair<Location, IElectricComponent> pair : this.electricComponenetsInstances) {
            pair.getSecond().onDestroyed(pair.getFirst().getWorld(), pair.getFirst(), false);
        }
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public static ElectroVanilla getInstance() {
        return Instance;
    }
}
