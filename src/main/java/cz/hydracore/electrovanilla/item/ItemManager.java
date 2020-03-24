package cz.hydracore.electrovanilla.item;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private Map<Integer, EItem> itemMap = new HashMap<>();

    public void register(@NotNull EItem eItem) {
        assert !itemMap.containsKey(eItem.getId());

        itemMap.put(eItem.getId(), eItem);
    }

    public EItem getItemById(int id) {
        return itemMap.getOrDefault(id, null);
    }

}
