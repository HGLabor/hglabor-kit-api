package de.hglabor.kitapi.paper;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.event.KitEventManager;
import de.hglabor.kitapi.kit.event.player.KitItemInteractEvent;
import de.hglabor.kitapi.kit.event.player.KitPlayerToggleSneakEvent;
import de.hglabor.kitapi.kit.item.KitItem;
import de.hglabor.kitapi.kit.kits.ThorKit;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KitApiPlugin extends JavaPlugin implements Listener {
    public static final Map<UUID, IKitPlayer> PLAYER_REGISTRY = new HashMap<>();

    @Override
    public void onEnable() {
        KitApi.init();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(toNMS(event.getItemDrop().getItemStack()).getOrCreateTag().contains(KitItem.UNDROPPABLE));
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        KitEventManager.call(new KitPlayerToggleSneakEvent(new PaperKitPlayer(event.getPlayer().getUniqueId()), event.isSneaking()));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && event.hasBlock() && event.getHand() != null) {
            KitEventManager.call(new KitItemInteractEvent(PLAYER_REGISTRY.computeIfAbsent(event.getPlayer().getUniqueId(), PaperKitPlayer::new), toNMS(event.getItem()), toNMS(event.getAction()), toNMS(event.getClickedBlock()), toNMS(event.useInteractedBlock()), toNMS(event.useItemInHand()), toNMS(event.getHand())));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(ThorKit.INSTANCE.getKitItem().getItemStack().asBukkitCopy());
    }

    public static net.minecraft.world.item.ItemStack toNMS(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    public static net.minecraft.world.level.block.Block toNMS(Block block) {
        return ((CraftBlock) block).getNMS().getBlock();
    }

    public static net.minecraft.world.entity.EquipmentSlot toNMS(EquipmentSlot slot) {
        return net.minecraft.world.entity.EquipmentSlot.values()[slot.ordinal()];
    }

    public static de.hglabor.kitapi.kit.event.Action toNMS(Action action) {
        return de.hglabor.kitapi.kit.event.Action.valueOf(action.name());
    }

    public static de.hglabor.kitapi.kit.event.Event.Result toNMS(Event.Result result) {
        return de.hglabor.kitapi.kit.event.Event.Result.valueOf(result.name());
    }
}
