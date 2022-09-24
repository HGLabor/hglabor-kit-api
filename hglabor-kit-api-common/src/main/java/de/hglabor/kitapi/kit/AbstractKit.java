package de.hglabor.kitapi.kit;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public abstract class AbstractKit {
    private final String name;
    private boolean isEnabled = true;

    protected AbstractKit(String name) {
        this.name = name;
    }

    public ItemStack getDisplayItem() {
        return new ItemStack(Items.STONE_SWORD).setHoverName(new TextComponent(name));
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }
}
