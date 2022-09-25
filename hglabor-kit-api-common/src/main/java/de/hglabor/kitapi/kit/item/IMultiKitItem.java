package de.hglabor.kitapi.kit.item;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IMultiKitItem extends IKitItemSupplier<IMultiKitItem> {
    List<ItemStack> getKitItems();
}
