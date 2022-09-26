package de.hglabor.kitapi.paper.player;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.player.AbstractKitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class PaperKitPlayer extends AbstractKitPlayer<Player> {

    public PaperKitPlayer(UUID uuid) {
        super(uuid);
    }

    @Override
    public boolean hasKit(AbstractKit kit) {
        return true;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }

    @Override
    public void sendCooldownInfo(ISingleCooldown cooldown) {
        getPlayer().ifPresent(player -> {
            long endTime = singleCooldowns.getOrDefault(cooldown, 0L);
            if (endTime > 0) {
                long remainingTime = endTime - System.currentTimeMillis();
                player.sendMessage("Cooldown " + remainingTime);
            }
        });
    }

    @Override
    public void sendCooldownInfo(IMultiCooldown cooldown, String key) {
        getPlayer().ifPresent(player -> {
            //player.sendMessage(new TextComponent("Cooldown"), Util.NIL_UUID);
        });
    }
}
