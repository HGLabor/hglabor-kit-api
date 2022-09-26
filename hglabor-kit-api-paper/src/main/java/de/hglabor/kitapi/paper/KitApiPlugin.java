package de.hglabor.kitapi.paper;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import de.hglabor.kitapi.paper.command.KitSettingsCommand;
import de.hglabor.kitapi.paper.kits.ManipulationKit;
import de.hglabor.kitapi.paper.kits.MultiKitItemDummy;
import de.hglabor.kitapi.paper.kits.NinjaKit;
import de.hglabor.kitapi.paper.kits.SnailKit;
import de.hglabor.kitapi.paper.kits.SwitcherKit;
import de.hglabor.kitapi.paper.kits.ThorKit;
import de.hglabor.kitapi.paper.player.PaperKitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KitApiPlugin extends JavaPlugin implements Listener {
    public static final Map<UUID, IKitPlayer> PLAYER_REGISTRY = new HashMap<>();

    @Override
    public void onEnable() {
        KitApi.init(uuid -> PLAYER_REGISTRY.computeIfAbsent(uuid, PaperKitPlayer::new), this);
        KitApi.register(ThorKit.INSTANCE);
        KitApi.register(NinjaKit.INSTANCE);
        KitApi.register(SnailKit.INSTANCE);
        KitApi.register(ManipulationKit.INSTANCE);
        KitApi.register(MultiKitItemDummy.INSTANCE);
        Bukkit.getPluginManager().registerEvents(this, this);
        KitSettingsCommand.register(((CraftServer) Bukkit.getServer()).getServer().vanillaCommandDispatcher.getDispatcher());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(event.getItemDrop().getItemStack().hasItemMeta() && event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(KitItemBuilder.MARKER));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager) {
            IKitPlayer kitDamager = KitApi.getKitPlayer(damager.getUniqueId());
            kitDamager.setLatestTarget(event.getEntity());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(ManipulationKit.INSTANCE.getKitItem());
        event.getPlayer().getInventory().addItem(ThorKit.INSTANCE.getKitItem());
        event.getPlayer().getInventory().addItem(SwitcherKit.INSTANCE.getKitItem());
        event.getPlayer().getInventory().addItem(MultiKitItemDummy.INSTANCE.getKitItems().toArray(new ItemStack[0]));
    }
}
