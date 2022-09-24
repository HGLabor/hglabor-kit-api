package de.hglabor.kitapi.kit.item;

import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public interface IPlaceableKitItem {
    default Predicate<ItemStack> getKitItemPredicate() {
        return itemStack -> true;
    }
}
