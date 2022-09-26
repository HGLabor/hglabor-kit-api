package de.hglabor.kitapi.kit.item;

import org.bukkit.inventory.ItemStack;

public interface ISingleKitItem extends IKitItemSupplier<ISingleKitItem> {
    ItemStack getKitItem();
}
