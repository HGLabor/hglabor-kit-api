package de.hglabor.kitapi.kit;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

import java.util.function.BiConsumer;

public abstract class AbstractKit implements Listener {
    private final String name;
    private boolean isEnabled = true;

    protected AbstractKit(String name) {
        this.name = name;
    }

    public ItemStack getDisplayItem() {
        return new ItemStack(Items.STONE_SWORD).setHoverName(new TextComponent(name));
    }

    public final boolean isEnabled() {
        return isEnabled;
    }

    public final void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public final String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public final <T extends PlayerEvent> void playerKitEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.LOW, (listener, event) -> {
            consumer.accept((T) event, (IKitPlayer) ((PlayerEvent) event).getPlayer());
        }, KitApi.getPlugin());
    }
}
