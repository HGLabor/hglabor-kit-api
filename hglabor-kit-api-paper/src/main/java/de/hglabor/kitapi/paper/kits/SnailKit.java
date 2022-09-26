package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.util.ChanceUtils;
import de.hglabor.kitapi.kit.util.EventUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnailKit extends AbstractKit {
    public final static SnailKit INSTANCE = new SnailKit();
    private final int likelihood, effectDuration, effectMultiplier;
    private final PotionEffectType effectType;

    private SnailKit() {
        super("Snail");
        likelihood = 25;
        effectDuration = 4;
        effectMultiplier = 0;
        effectType = PotionEffectType.SLOW;
        playerKitEvent(EntityDamageByEntityEvent.class, EventUtils::getAttacker, (event, kitPlayer) -> {
            if (event.getEntity() instanceof LivingEntity livingEntity && ChanceUtils.roll(likelihood)) {
                livingEntity.addPotionEffect(new PotionEffect(effectType, effectDuration * 20, effectMultiplier, true, true));
            }
        });
    }
}
