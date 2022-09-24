package de.hglabor.kitapi.paper;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.player.AbstractKitPlayer;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;

import java.util.Optional;
import java.util.UUID;

public class PaperKitPlayer extends AbstractKitPlayer {
    public PaperKitPlayer(org.bukkit.entity.Player player) {
        this(player.getUniqueId());
    }

    public PaperKitPlayer(UUID uuid) {
        super(uuid);
    }

    @Override
    public void addCooldown(ISingleCooldown cooldown) {
    }

    @Override
    public void addCooldown(IMultiCooldown cooldown) {
    }

    @Override
    public boolean hasKit(AbstractKit kit) {
        return true;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle());
    }
}
