package de.hglabor.kitapi.kit;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public abstract class AbstractKit {
    private final String name;
    private boolean isEnabled;

    protected AbstractKit(String name) {
        this.name = name;
    }

    public ItemStack getDisplayItem() {
        return new ItemStack(Items.STONE_SWORD).setHoverName(Component.literal(name));
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
