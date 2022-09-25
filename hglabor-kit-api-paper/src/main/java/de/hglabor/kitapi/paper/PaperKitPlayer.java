package de.hglabor.kitapi.paper;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.player.AbstractKitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class PaperKitPlayer extends AbstractKitPlayer<Player> {
    public PaperKitPlayer(Player player) {
        this(player.getUniqueId());
    }

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
}
