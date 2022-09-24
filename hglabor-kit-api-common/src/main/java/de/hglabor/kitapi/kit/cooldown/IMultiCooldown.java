package de.hglabor.kitapi.kit.cooldown;

import java.util.Map;

public interface IMultiCooldown extends ICooldown<IMultiCooldown> {
    Map<String, Float> getCooldowns();
}
