package de.hglabor.kitapi.kit.item;


import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public interface IPlaceableKitItem {
    default Predicate<ItemStack> getKitItemPredicate() {
        return itemStack -> true;
    }
}
