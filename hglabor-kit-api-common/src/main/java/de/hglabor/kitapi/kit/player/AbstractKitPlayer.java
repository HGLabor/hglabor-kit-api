package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractKitPlayer<T> implements IKitPlayer {
    protected final UUID uuid;
    private final Map<ISingleCooldown, Long> singleCooldowns = new HashMap<>();
    private final Map<IMultiCooldown, Map<String, Long>> multiCooldowns = new HashMap<>();

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
    public void sendCooldownInfo(ISingleCooldown cooldown) {
        getPlayer().ifPresent(player -> {
            long endTime = singleCooldowns.getOrDefault(cooldown, 0L);
            if (endTime > 0) {
                long remainingTime = endTime - System.currentTimeMillis();
                //player.sendMessage(new TextComponent("Cooldown " + remainingTime), Util.NIL_UUID);
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
