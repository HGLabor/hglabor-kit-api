package de.hglabor.kitapi.kit.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class EventUtils {
    private EventUtils() {
    }

    public static Player getPlayer(Event event) {
        if (event instanceof EntityDamageByEntityEvent damageEvent) {
            return getAttacker(damageEvent);
        } else if (event instanceof ProjectileHitEvent hitEvent) {
            return getShooter(hitEvent);
        } else if (event instanceof ProjectileLaunchEvent launchEvent) {
            return getLauncher(launchEvent);
        } else if (event instanceof PlayerEvent playerEvent) {
            return playerEvent.getPlayer();
        }
        return null;
    }

    public static Player getAttacker(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) return player;
        return null;
    }

    public static Player getShooter(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) return player;
        return null;
    }

    public static Player getLauncher(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) return player;
        return null;
    }

    public static boolean isRightClick(PlayerInteractEvent event) {
        return event.getAction().isRightClick();
    }
}
