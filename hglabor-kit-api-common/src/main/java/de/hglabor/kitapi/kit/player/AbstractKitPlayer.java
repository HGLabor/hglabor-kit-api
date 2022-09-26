package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractKitPlayer<T> implements IKitPlayer {
    protected final UUID uuid;
    protected final Map<ISingleCooldown, Long> singleCooldowns = new HashMap<>();
    protected final Map<IMultiCooldown, Map<String, Long>> multiCooldowns = new HashMap<>();
    protected Pair<Entity, Long> latestTarget = Pair.of(null, 0L);

    protected AbstractKitPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public final void addCooldown(ISingleCooldown cooldown) {
        singleCooldowns.put(cooldown, System.currentTimeMillis() + (long) (cooldown.getCooldown() * 1000L));
    }

    @Override
    public final boolean hasCooldown(ISingleCooldown cooldown) {
        return System.currentTimeMillis() < singleCooldowns.getOrDefault(cooldown, 0L);
    }

    @Override
    public final void addCooldown(IMultiCooldown cooldown) {
    }

    @Override
    public final boolean hasCooldown(IMultiCooldown cooldown, String key) {
        return false;
    }

    @Override
    public final UUID getUuid() {
        return uuid;
    }

    @Override
    public Pair<@Nullable Entity, Long> getLatestTarget() {
        return latestTarget;
    }

    @Override
    public void setLatestTarget(Entity entity) {
        this.latestTarget = Pair.of(entity, System.currentTimeMillis());
    }
}
