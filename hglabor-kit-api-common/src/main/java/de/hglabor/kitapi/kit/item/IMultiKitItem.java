package de.hglabor.kitapi.kit.item;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IMultiKitItem extends IKitItem<IMultiKitItem>{
    List<ItemStack> getKitItems();
}
