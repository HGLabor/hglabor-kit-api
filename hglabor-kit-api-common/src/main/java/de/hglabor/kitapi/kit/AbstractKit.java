package de.hglabor.kitapi.kit;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.item.IKitItemSupplier;
import de.hglabor.kitapi.kit.item.IMultiKitItem;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import de.hglabor.kitapi.kit.util.EventUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractKit implements Listener {
    private final String name;
    private boolean isEnabled = true;

    protected AbstractKit(String name) {
        this.name = name;
    }

    public ItemStack getDisplayItem() {
        return new ItemStack(Material.STONE_SWORD);
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

    public void onEnable(IKitPlayer kitPlayer) {
    }

    public void onDisable(IKitPlayer kitPlayer) {
        onDeactivation(kitPlayer);
    }

    public void onDeactivation(IKitPlayer kitPlayer) {
    }

    @SuppressWarnings("unchecked")
    public final <T extends Event> void event(Class<T> clazz, Consumer<T> consumer) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                if (isEnabled()) {
                    consumer.accept((T) event);
                }
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final <T extends Event> void playerKitEventWithKitItem(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer) {
        playerKitEvent(clazz, EventUtils::getPlayer, consumer, false, t -> true, "", true);
    }

    public final <T extends PlayerEvent> void playerKitEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, Function<T, Boolean> sendCooldownMessage) {
        playerKitEvent(clazz, consumer, false, sendCooldownMessage, "", false);
    }

    public final <T extends Event> void playerKitEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer) {
        playerKitEvent(clazz, EventUtils::getPlayer, consumer, false, t -> true, "", false);
    }

    public final <T extends Event> void playerKitEvent(Class<T> clazz, Function<T, Player> playerGetter, BiConsumer<T, IKitPlayer> consumer) {
        playerKitEvent(clazz, playerGetter, consumer, false, t -> true, "", false);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer) {
        onKitItemRightClick(consumer, false, event -> true, "", null);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, ItemStack itemStack) {
        System.out.println("###ItemStack " + itemStack);
        onKitItemRightClick(consumer, false, event -> true, "", itemStack);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, boolean ignoreCooldown, Function<PlayerInteractEvent, Boolean> sendCooldownMessage, String cooldownKey, ItemStack kitItem) {
        Bukkit.getPluginManager().registerEvent(PlayerInteractEvent.class, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                if (((PlayerInteractEvent) event).getAction().isRightClick()) {
                    System.out.println(listener);
                    ((PlayerInteractEvent) event).setCancelled(true);
                    handlePlayerEvent(consumer, event, ((PlayerInteractEvent) event).getPlayer(), ignoreCooldown, sendCooldownMessage, cooldownKey, true, kitItem);
                }
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer) {
        onKitItemRightClickAtEntity(consumer, false, event -> true, "");
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer, boolean ignoreCooldown, Function<PlayerInteractEntityEvent, Boolean> sendCooldownMessage, String cooldownKey) {
        Bukkit.getPluginManager().registerEvent(PlayerInteractEntityEvent.class, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                ((PlayerInteractEntityEvent) event).setCancelled(true);
                handlePlayerEvent(consumer, event, ((PlayerInteractEntityEvent) event).getPlayer(), ignoreCooldown, sendCooldownMessage, cooldownKey, true, null);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final void clickKitItemEvent(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer) {
        playerKitEvent(PlayerInteractEvent.class, consumer, false, playerInteractEvent -> (true), "", true);
    }

    public final void clickKitItemEvent(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, Function<PlayerInteractEvent, Boolean> sendCooldownMessage) {
        playerKitEvent(PlayerInteractEvent.class, consumer, false, sendCooldownMessage, "", true);
    }

    public final void clickEntityWithKitItemEvent(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer) {
        playerKitEvent(PlayerInteractEntityEvent.class, consumer, false, playerInteractEvent -> (true), "", true);
    }

    @SuppressWarnings("unchecked")
    public final <T extends Event> void playerKitEvent(Class<T> clazz, Function<T, Player> playerGetter, BiConsumer<T, IKitPlayer> consumer, boolean ignoreCooldown, Function<T, Boolean> sendCooldownMessage, String cooldownKey, boolean withKitItem) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                handlePlayerEvent(consumer, event, playerGetter.apply((T) event), ignoreCooldown, sendCooldownMessage, cooldownKey, withKitItem, null);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final <T extends Event> void playerKitEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, boolean ignoreCooldown, Function<T, Boolean> sendCooldownMessage, String cooldownKey, boolean withKitItem) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                handlePlayerEvent(consumer, event, ((PlayerEvent) event).getPlayer(), ignoreCooldown, sendCooldownMessage, cooldownKey, withKitItem, null);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> void handlePlayerEvent(BiConsumer<T, IKitPlayer> consumer, Event event, @Nullable Player player, boolean ignoreCooldown, Function<T, Boolean> sendCooldownMessage, String cooldownKey, boolean withKitItem, ItemStack kitItem) {
        if (player == null) return;
        IKitPlayer kitPlayer = KitApi.getKitPlayer(player.getUniqueId());
        if (!isEnabled()) return;
        if (!kitPlayer.hasKit(this)) return;
        if (withKitItem && this instanceof IKitItemSupplier<?> kitItemSupplier) {
            EquipmentSlot hand = EquipmentSlot.HAND;
            if (event instanceof PlayerInteractEvent interactEvent) {
                hand = interactEvent.getHand() != null ? interactEvent.getHand() : EquipmentSlot.HAND;
            } else if (event instanceof PlayerInteractEntityEvent interactEntityEvent) {
                hand = interactEntityEvent.getHand();
            }

            ItemStack itemInMainHand = player.getInventory().getItem(hand);
            if (kitItemSupplier instanceof ISingleKitItem singleKitItem) {
                if (!itemInMainHand.isSimilar(singleKitItem.getKitItem())) return;
                //TODO maybe brauchen wir eine eigene methode anstatt isSimilar
            } else if (kitItemSupplier instanceof IMultiKitItem multiKitItem) {
                //Bukkit.broadcastMessage(this.getName());
                //Bukkit.broadcastMessage(kitItem != null ? kitItem.toString() : "null");
                if (!itemInMainHand.isSimilar(kitItem)) return;
            }
        }
        if (!ignoreCooldown) {
            if (this instanceof ISingleCooldown singleCooldown) {
                if (kitPlayer.hasCooldown(singleCooldown)) {
                    if (sendCooldownMessage.apply((T) event)) {
                        kitPlayer.sendCooldownInfo(singleCooldown);
                    }
                    if (event instanceof PlayerInteractEvent) {
                        ((PlayerInteractEvent) event).setCancelled(true);
                    }
                    return;
                }
            } else if (this instanceof IMultiCooldown multiCooldown) {
                if (kitPlayer.hasCooldown(multiCooldown, cooldownKey)) {
                    if (sendCooldownMessage.apply((T) event)) {
                        kitPlayer.sendCooldownInfo(multiCooldown, cooldownKey);
                    }
                    if (event instanceof PlayerInteractEvent) {
                        ((PlayerInteractEvent) event).setCancelled(true);
                    }
                    return;
                }
            }
        }
        consumer.accept((T) event, kitPlayer);
    }

    private record ListenerWrapper(UUID uuid) implements Listener {
    }
}
