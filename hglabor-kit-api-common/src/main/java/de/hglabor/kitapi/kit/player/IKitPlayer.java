package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface IKitPlayer {
    void addCooldown(ISingleCooldown cooldown);

    void addCooldown(IMultiCooldown cooldown, String key);

    boolean hasCooldown(ISingleCooldown cooldown);

    boolean hasCooldown(IMultiCooldown cooldown, String key);

    UUID getUuid();

    boolean hasKit(AbstractKit kit);

    Optional<Player> getPlayer();

    void sendCooldownInfo(ISingleCooldown cooldown);

    void sendCooldownInfo(IMultiCooldown cooldown, String key);

    Pair<@Nullable Entity, Long> getLatestTarget();

    void setLatestTarget(Entity entity);
}
