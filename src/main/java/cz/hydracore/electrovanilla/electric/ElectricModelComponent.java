package cz.hydracore.electrovanilla.electric;

import cz.hydracore.electrovanilla.ElectroVanilla;
import cz.hydracore.electrovanilla.item.EItem;
import cz.hydracore.electrovanilla.model.AbstractModel;
import cz.hydracore.electrovanilla.world.ElectricBlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public abstract class ElectricModelComponent implements IElectricComponent {

    protected AbstractModel model;
    protected EItem eItem;

    public ElectricModelComponent(EItem eItem) {
        this.eItem = eItem;
    }

    private static int i;

    @Override
    public void onSpawned(@NotNull World world, @NotNull Location location) {
        assert model == null;

        Class<? extends AbstractModel> modelClass = getModelClass();

        if(modelClass == null) {
            ElectroVanilla.getInstance().getLogger().log(Level.WARNING, getClass().getName() + " has null model !");
            return;
        }

        try {
            this.model = getModelClass().getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            ex.printStackTrace();
        }

        this.model.spawn(location);
    }

    @Override
    public void onDestroyed(@NotNull World world, @NotNull Location location, boolean isDrop) {
        this.model.destroy();

        if(!isDrop) {
            return;
        }

        world.dropItemNaturally(location, eItem.getItemStack().clone());
        this.dropItemsOnDestroy(world, location);
    }

    public void update(ElectricBlockStorage storage) {
        for (int i = 0; i < 6; i++) {
            BlockFace face = BlockFace.values()[i];

            ElectricBlockStorage relativeStorage = storage.getRelative(face);

            this.model.setAttribute(i, relativeStorage != null || storage.getLocation().getBlock().getRelative(face).getType() == Material.FURNACE);
        }
    }

    public abstract void dropItemsOnDestroy(@NotNull World world, @NotNull Location location);

    public abstract Class<? extends AbstractModel> getModelClass();

    public AbstractModel getModel() {
        return model;
    }

    public EItem geteItem() {
        return eItem;
    }
}
