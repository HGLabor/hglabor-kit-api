package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractKitPlayer implements IKitPlayer {
    protected final UUID uuid;
    private final Map<ISingleCooldown, Float> singleCooldowns = new HashMap<>();
    private final Map<IMultiCooldown, Map<String, Float>> multiCooldowns = new HashMap<>();

    protected AbstractKitPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void addCooldown(ISingleCooldown cooldown) {
        singleCooldowns.put(cooldown, cooldown.getCooldown());
    }

    @Override
    public boolean hasCooldown(IMultiCooldown cooldown, String key) {
        return false;
    }

    @Override
    public boolean hasCooldown(ISingleCooldown cooldown) {
        return singleCooldowns.getOrDefault(cooldown, 0f) > 0f;
    }

    @Override
    public final UUID getUuid() {
        return uuid;
    }

    @Override
    public void sendCooldownInfo(ISingleCooldown cooldown) {
        getPlayer().ifPresent(player -> {
            player.sendMessage(new TextComponent("Cooldown"), Util.NIL_UUID);
        });
    }

    @Override
    public void sendCooldownInfo(IMultiCooldown cooldown, String key) {
        getPlayer().ifPresent(player -> {
            player.sendMessage(new TextComponent("Cooldown"), Util.NIL_UUID);
        });
    }
}
