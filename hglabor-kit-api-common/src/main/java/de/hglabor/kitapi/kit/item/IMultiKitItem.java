package de.hglabor.kitapi.kit.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMultiKitItem extends IKitItemSupplier<IMultiKitItem> {
    List<ItemStack> getKitItems();
}
