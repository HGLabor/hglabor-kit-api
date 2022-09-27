package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SoulstealerKit extends AbstractKit {
    public static final SoulstealerKit INSTANCE = new SoulstealerKit();

    private SoulstealerKit() {
        super("Soulstealer");
        onKitPlayerEvent(PlayerDeathEvent.class, (playerDeathEvent, kitPlayer) -> {
            if (kitPlayer.getKitAttributeOrDefault("death", 0) >= 3) return;
            kitPlayer.putKitAttribute("death", kitPlayer.getKitAttributeOrDefault("death", 0) + 1);
            playerDeathEvent.setCancelled(true);
        });
        onKitPlayerKillsEntity((entityDeathEvent, kitPlayer) -> {
            Player player = kitPlayer.getPlayer().orElseThrow();
            player.setMaxHealth(player.getMaxHealth() + 2);
        });
    }
}
