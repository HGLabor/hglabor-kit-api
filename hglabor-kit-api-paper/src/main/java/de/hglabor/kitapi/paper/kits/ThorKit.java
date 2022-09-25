package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.event.KitEventHandler;
import de.hglabor.kitapi.kit.event.player.KitItemInteractEvent;
import de.hglabor.kitapi.kit.item.IKitItem;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItem;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import de.hglabor.kitapi.paper.KitApiPlugin;
import net.minecraft.world.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ThorKit extends AbstractKit implements ISingleKitItem, ISingleCooldown, Listener {
    public static final ThorKit INSTANCE = new ThorKit();
    private static final IKitItem KIT_ITEM = new KitItem(Items.WOODEN_AXE).makeUnbreakable();

    private ThorKit() {
        super("Thor");
        kitEvent(PlayerInteractEvent.class, (event, player) -> {
            event.getPlayer().sendMessage("Hi");
        });
        kitEvent(BlockBreakEvent.class, blockBreakEvent -> (IKitPlayer) blockBreakEvent.getPlayer(), (blockBreakEvent, iKitPlayer) -> {

        });
    }

    public <T extends PlayerEvent> void kitEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.LOW, (listener, event) -> {
            consumer.accept((T) event, (IKitPlayer) ((PlayerEvent) event).getPlayer());
        }, KitApiPlugin.getPlugin(KitApiPlugin.class));
    }

    public <T extends Event> void kitEvent(Class<T> clazz, Function<T, IKitPlayer> supplier, BiConsumer<T, IKitPlayer> consumer) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.LOW, (listener, event) -> {
            consumer.accept((T) event, (IKitPlayer) ((PlayerEvent) event).getPlayer());
        }, KitApiPlugin.getPlugin(KitApiPlugin.class));
    }

    @KitEventHandler
    public void onKitItemInteract(KitItemInteractEvent<Player, PlayerInteractEvent> event) {
        if (event.getData().getAction().isRightClick()) {
            event.getKitPlayer().getPlayer().ifPresent(player -> {
                player.getWorld().strikeLightningEffect(player.getLocation());
            });
            event.getKitPlayer().addCooldown(this);
        }
    }

    @Override
    public IKitItem getKitItem() {
        return KIT_ITEM;
    }

    @Override
    public float getCooldown() {
        return 5f;
    }
}
