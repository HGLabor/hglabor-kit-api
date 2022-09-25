package de.hglabor.kitapi.kit.event.player;

import de.hglabor.kitapi.kit.player.IKitPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitItemInteractEvent<Player, Data> extends KitPlayerEvent<Player> {
    private final Data data;
    private final ItemStack itemStack;

    public KitItemInteractEvent(@NotNull IKitPlayer who, Data data, ItemStack itemStack) {
        super(who);
        this.data = data;
        this.itemStack = itemStack;
    }

    public Data getData() {
        return data;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
