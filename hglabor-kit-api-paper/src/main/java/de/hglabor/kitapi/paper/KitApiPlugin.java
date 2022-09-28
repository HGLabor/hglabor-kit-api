package de.hglabor.kitapi.paper;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import de.hglabor.kitapi.paper.command.KitSettingsCommand;
import de.hglabor.kitapi.paper.kits.*;
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
        KitApi.register(SoulstealerKit.INSTANCE);
        KitApi.register(new DiggerKit());
        KitApi.register(new LumberjackKit());
        KitApi.register(new KayaKit());
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
        for (AbstractKit kit : KitApi.KIT_REGISTRY) {
            event.getPlayer().getInventory().addItem(kit.getKitItems().toArray(new ItemStack[0]));
        }
    }
}
