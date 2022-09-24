package de.hglabor.kitapi.kit.player;

import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKitPlayer implements IKitPlayer {
    private final Map<ISingleCooldown, Float> singleCooldowns = new HashMap<>();
    private final Map<IMultiCooldown, Map<String, Float>> multiCooldowns = new HashMap<>();

    @Override
    public boolean hasCooldown(IMultiCooldown cooldown, String key) {
        return false;
    }

    @Override
    public boolean hasCooldown(ISingleCooldown cooldown) {
        return singleCooldowns.getOrDefault(cooldown, 0f) > 0f;
    }
}
