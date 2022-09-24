package de.hglabor.kitapi.kit.item;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class KitItem implements IKitItemStack {
    public static final String MARKER = "kit-item";
    public static final String UNDROPPABLE = "undroppable";
    private final ItemStack itemStack;

    public KitItem(ItemLike itemLike) {
        this.itemStack = new ItemStack(itemLike);
        this.itemStack.addTagElement(MARKER, ByteTag.ONE);
        this.itemStack.addTagElement(UNDROPPABLE, ByteTag.ONE);
    }

    public KitItem withAmount(int amount) {
        this.itemStack.setCount(amount);
        return this;
    }

    public KitItem makeDroppable() {
        this.itemStack.removeTagKey(UNDROPPABLE);
        return this;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}
