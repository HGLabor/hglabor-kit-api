package de.hglabor.kitapi.kit.item;

import net.minecraft.world.item.ItemStack;

public interface ISingleKitItem extends IKitItem<ISingleKitItem> {
    ItemStack getKitItem();
}
