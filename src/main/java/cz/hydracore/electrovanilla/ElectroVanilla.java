package cz.hydracore.electrovanilla;

import com.mojang.datafixers.util.Pair;
import cz.hydracore.electrovanilla.conduit.WireComponent;
import cz.hydracore.electrovanilla.electric.ElectricModelComponent;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.item.ItemManager;
import cz.hydracore.electrovanilla.listeners.PoopEvents;
import cz.hydracore.electrovanilla.world.ElectricWorld;
import cz.hydracore.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElectroVanilla extends JavaPlugin implements Listener {

    private static ElectroVanilla Instance;

    private ItemManager itemManager;
    private ElectricWorld electricWorld;

    @Override
    public void onEnable() {
        Instance = this;

        itemManager = new ItemManager();
        electricWorld = new ElectricWorld();

        itemManager.register(new EItem(0, new ItemBuilder(Material.COCOA_BEANS).setName("Iron dust").build()));
        itemManager.register(new EItem(4, new ItemBuilder(Material.BLAZE_POWDER).setName("Gold dust").build()));

        {
            ItemStack itemStack = new ItemBuilder(Material.BLACK_WOOL).setNbt_Int("ITEM_ID", 1).setName("Wire").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "wire"), itemStack);
            recipe.shape("   ", "WRW", "   ");
            recipe.setIngredient('W', Material.IRON_INGOT);
            recipe.setIngredient('R', Material.REDSTONE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(1, itemStack, WireComponent::new, recipe));
        }

        {
            ItemStack itemStack = new ItemBuilder(Material.CHEST_MINECART).setNbt_Int("ITEM_ID", 10).setName("Ender pouch").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "ender_pouch"), itemStack);
            recipe.shape("PPP", "RER", "PPP");
            recipe.setIngredient('P', Material.ENDER_PEARL);
            recipe.setIngredient('R', Material.REDSTONE);
            recipe.setIngredient('E', Material.ENDER_CHEST);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(10, itemStack, null, recipe));
        }

        {
            ItemStack itemStack = new ItemBuilder(Material.SMITHING_TABLE).setName("Pulverizer").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "pulverizer"), itemStack);
            recipe.shape(" B ", " F ", " B ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(2, itemStack, null, recipe));
        }

        {
            ItemStack itemStack = new ItemBuilder(Material.FLETCHING_TABLE).setName("Iron furnace").build();

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "iron_furnace"), itemStack);
            recipe.shape(" B ", " F ", " F ");
            recipe.setIngredient('B', Material.IRON_BLOCK);
            recipe.setIngredient('F', Material.FURNACE);
            Bukkit.addRecipe(recipe);

            itemManager.register(new EItem(3, itemStack, null, recipe));
        }

        {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "membrane"), new ItemStack(Material.PHANTOM_MEMBRANE, 3));
            recipe.shape("EEE", "FPF", "EEE");
            recipe.setIngredient('E', Material.END_STONE);
            recipe.setIngredient('F', Material.FEATHER);
            recipe.setIngredient('P', Material.ENDER_PEARL);
            Bukkit.addRecipe(recipe);
        }

        {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "wool_to_string"), new ItemStack(Material.STRING, 4));
            recipe.shape("W");
            recipe.setIngredient('W', Material.WHITE_WOOL);
            Bukkit.addRecipe(recipe);
        }

        getServer().getPluginManager().registerEvents(this, this);
        //getServer().getPluginManager().registerEvents(new PoopEvents(), this);

        RunnableHelper.runTaskTimer(this::updateMachines, 10);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack itemStack = event.getItemInHand();

        int itemId = Nbt.getNbt_Int(itemStack, "ITEM_ID", -1);

        if(itemId == -1) {
            return;
        }


        EItem eItem = itemManager.getItemById(itemId);

        if(eItem.getId() == 10) {
            event.setCancelled(true);
            player.openInventory(player.getEnderChest());
            return;
        }

        if(this.electricWorld.spawn(eItem, block.getLocation()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == null)
            return;

        int itemId = Nbt.getNbt_Int(event.getItem(), "ITEM_ID", -1);

        if(itemId == -1) {
            return;
        }


        EItem eItem = itemManager.getItemById(itemId);

        if(eItem.getId() == 10) {
            event.setCancelled(true);
            player.openInventory(player.getEnderChest());
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        if(itemStack == null)
            return;

        int itemId = Nbt.getNbt_Int(itemStack, "ITEM_ID", -1);

        if(itemId == -1) {
            return;
        }


        EItem eItem = itemManager.getItemById(itemId);

        if(eItem.getId() == 10) {
            player.openInventory(player.getEnderChest());
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if(event.getRightClicked() instanceof ArmorStand) {
            this.electricWorld.destroy(event.getRightClicked().getLocation().getBlock().getLocation());
        }

        //event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        electricWorld.destroy(location);
    }

    @EventHandler
    public void onExplodeEvent(EntityExplodeEvent e) {
        if(e.getEntity() instanceof Creeper) {
            e.setCancelled(true);
        }
    }

    private void updateMachines() {
        /*for(Pair<Location, Machine> machinePair : this.machines) {
            machinePair.getSecond().update();
        }*/
    }

    public static boolean equalsBlockLocation(Location loc1, Location loc2) {
        return loc1.getWorld() == loc2.getWorld() && loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    @Override
    public void onDisable() {
        this.electricWorld.destroyAll();
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public static ElectroVanilla getInstance() {
        return Instance;
    }
}
