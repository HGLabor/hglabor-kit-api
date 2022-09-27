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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

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
    public final <T extends Event> void onEvent(Class<T> clazz, Consumer<T> consumer) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                if (isEnabled()) {
                    consumer.accept((T) event);
                }
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final void onKitPlayerAttacksEntity(BiConsumer<EntityDamageByEntityEvent, IKitPlayer> consumer) {
        onKitPlayerEvent(EntityDamageByEntityEvent.class, EventUtils::getAttacker, consumer, false, t -> true, "", false, null);
    }

    public final void onKitPlayerKillsEntity(BiConsumer<EntityDeathEvent, IKitPlayer> consumer) {
        onKitPlayerEvent(EntityDeathEvent.class, EventUtils::getKiller, consumer, false, t -> true, "", false, null);
    }

    public final void onKitPlayerKillsEntity(BiConsumer<EntityDeathEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitPlayerEvent(EntityDeathEvent.class, EventUtils::getKiller, consumer, false, t -> true, "", true, itemStack);
    }

    public final <T extends PlayerEvent> void onKitPlayerEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, Function<T, Boolean> sendCooldownMessage) {
        onKitPlayerEvent(clazz, consumer, false, sendCooldownMessage, "", false);
    }

    public final <T extends PlayerEvent> void onKitPlayerEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, Function<T, Boolean> sendCooldownMessage, boolean ignoreCooldown) {
        onKitPlayerEvent(clazz, consumer, ignoreCooldown, sendCooldownMessage, "", false);
    }

    public final <T extends Event> void onKitPlayerEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer) {
        onKitPlayerEvent(clazz, EventUtils::getPlayer, consumer, false, t -> true, "", false, null);
    }

    public final <T extends Event> void onKitPlayerEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, boolean ignoreCooldown) {
        onKitPlayerEvent(clazz, EventUtils::getPlayer, consumer, ignoreCooldown, t -> true, "", false, null);
    }

    public final <T extends Event> void onKitPlayerEvent(Class<T> clazz, Function<T, Player> playerGetter, BiConsumer<T, IKitPlayer> consumer) {
        onKitPlayerEvent(clazz, playerGetter, consumer, false, t -> true, "", false, null);
    }

    public final void onKitItemPlace(BiConsumer<BlockPlaceEvent, IKitPlayer> consumer) {
        onKitPlayerEvent(BlockPlaceEvent.class, EventUtils::getPlayer, consumer, false, t -> true, "", true, null);
    }

    public final void onKitItemPlace(BiConsumer<BlockPlaceEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitPlayerEvent(BlockPlaceEvent.class, EventUtils::getPlayer, consumer, false, t -> true, "", true, itemStack);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer) {
        onKitItemClick(consumer, false, event -> true, "", null, Action::isRightClick);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitItemClick(consumer, false, event -> true, "", itemStack, Action::isRightClick);
    }

    public final void onKitItemRightClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, ItemStack itemStack, String cooldownKey) {
        onKitItemClick(consumer, false, event -> true, cooldownKey, itemStack, Action::isRightClick);
    }

    public final void onKitItemLeftClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer) {
        onKitItemClick(consumer, false, event -> true, "", null, Action::isLeftClick);
    }

    public final void onKitItemLeftClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitItemClick(consumer, false, event -> true, "", itemStack, Action::isLeftClick);
    }

    public final void onKitItemLeftClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, ItemStack itemStack, String cooldownKey) {
        onKitItemClick(consumer, false, event -> true, cooldownKey, itemStack, Action::isLeftClick);
    }

    public final void onKitItemClick(BiConsumer<PlayerInteractEvent, IKitPlayer> consumer, boolean ignoreCooldown, Function<PlayerInteractEvent, Boolean> sendCooldownMessage, String cooldownKey, ItemStack kitItem, Function<Action, Boolean> actionGetter) {
        Bukkit.getPluginManager().registerEvent(PlayerInteractEvent.class, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                if (actionGetter.apply(((PlayerInteractEvent) event).getAction())) {
                    ItemStack item = ((PlayerInteractEvent) event).getItem();
                    if (item != null && isKitItem(item, kitItem)) {
                        ((PlayerInteractEvent) event).setCancelled(true);
                    }
                    handlePlayerEvent(consumer, event, ((PlayerInteractEvent) event).getPlayer(), ignoreCooldown, sendCooldownMessage, cooldownKey, true, kitItem);
                }
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer) {
        onKitItemRightClickAtEntity(consumer, false, event -> true, "", null);
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitItemRightClickAtEntity(consumer, false, event -> true, "", itemStack);
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer, ItemStack itemStack, String cooldownKey) {
        onKitItemRightClickAtEntity(consumer, false, event -> true, cooldownKey, itemStack);
    }

    public final void onKitItemRightClickAtEntity(BiConsumer<PlayerInteractEntityEvent, IKitPlayer> consumer, boolean ignoreCooldown, Function<PlayerInteractEntityEvent, Boolean> sendCooldownMessage, String cooldownKey, ItemStack itemStack) {
        Bukkit.getPluginManager().registerEvent(PlayerInteractEntityEvent.class, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                ((PlayerInteractEntityEvent) event).setCancelled(true);
                handlePlayerEvent(consumer, event, ((PlayerInteractEntityEvent) event).getPlayer(), ignoreCooldown, sendCooldownMessage, cooldownKey, true, itemStack);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final void onKitItemLeftClickAtEntity(BiConsumer<EntityDamageByEntityEvent, IKitPlayer> consumer) {
        onKitItemLeftClickAtEntity(consumer, false, event -> true, "", null);
    }

    public final void onKitItemLeftClickAtEntity(BiConsumer<EntityDamageByEntityEvent, IKitPlayer> consumer, ItemStack itemStack) {
        onKitItemLeftClickAtEntity(consumer, false, event -> true, "", itemStack);
    }

    public final void onKitItemLeftClickAtEntity(BiConsumer<EntityDamageByEntityEvent, IKitPlayer> consumer, ItemStack itemStack, String cooldownKey) {
        onKitItemLeftClickAtEntity(consumer, false, event -> true, cooldownKey, itemStack);
    }

    public final void onKitItemLeftClickAtEntity(BiConsumer<EntityDamageByEntityEvent, IKitPlayer> consumer, boolean ignoreCooldown, Function<EntityDamageByEntityEvent, Boolean> sendCooldownMessage, String cooldownKey, ItemStack itemStack) {
        Bukkit.getPluginManager().registerEvent(EntityDamageByEntityEvent.class, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                handlePlayerEvent(consumer, event, EventUtils.getPlayer(event), ignoreCooldown, sendCooldownMessage, cooldownKey, true, itemStack);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    @SuppressWarnings("unchecked")
    public final <T extends Event> void onKitPlayerEvent(Class<T> clazz, Function<T, Player> playerGetter, BiConsumer<T, IKitPlayer> consumer, boolean ignoreCooldown, Function<T, Boolean> sendCooldownMessage, String cooldownKey, boolean withKitItem, ItemStack item) {
        Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
            try {
                handlePlayerEvent(consumer, event, playerGetter.apply((T) event), ignoreCooldown, sendCooldownMessage, cooldownKey, withKitItem, item);
            } catch (ClassCastException ignored) {
            }
        }, KitApi.getPlugin());
    }

    public final <T extends Event> void onKitPlayerEvent(Class<T> clazz, BiConsumer<T, IKitPlayer> consumer, boolean ignoreCooldown, Function<T, Boolean> sendCooldownMessage, String cooldownKey, boolean withKitItem) {
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
            } else if (event instanceof BlockPlaceEvent blockPlaceEvent) {
                hand = blockPlaceEvent.getHand();
            }

            ItemStack itemInMainHand = player.getInventory().getItem(hand);
            if (kitItemSupplier instanceof ISingleKitItem singleKitItem) {
                if (!itemInMainHand.isSimilar(singleKitItem.getKitItem())) return;
                //TODO maybe brauchen wir eine eigene methode anstatt isSimilar
            } else if (kitItemSupplier instanceof IMultiKitItem multiKitItem) {
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
                    } else if (event instanceof BlockPlaceEvent placeEvent) {
                        placeEvent.setCancelled(true);
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
                    } else if (event instanceof BlockPlaceEvent placeEvent) {
                        placeEvent.setCancelled(true);
                    }
                    return;
                }
            }
        }
        consumer.accept((T) event, kitPlayer);
    }

    public final BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(KitApi.getPlugin(), runnable, delay);
    }

    private boolean isKitItem(ItemStack item, ItemStack kitItem) {
        return item.isSimilar(kitItem);
    }
}
