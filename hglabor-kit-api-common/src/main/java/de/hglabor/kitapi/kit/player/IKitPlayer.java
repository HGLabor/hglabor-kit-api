package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.UUID;

public interface IKitPlayer {
    void addCooldown(ISingleCooldown cooldown);

    void addCooldown(IMultiCooldown cooldown);

    boolean hasCooldown(ISingleCooldown cooldown);

    boolean hasCooldown(IMultiCooldown cooldown, String key);

    UUID getUuid();

    boolean hasKit(AbstractKit kit);

    Optional<Player> getPlayer();

    void sendCooldownInfo(ISingleCooldown cooldown);
    void sendCooldownInfo(IMultiCooldown cooldown, String key);
}
