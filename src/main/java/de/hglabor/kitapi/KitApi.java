package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.KitEventManager;
import de.hglabor.kitapi.kit.event.player.KitPlayerToggleSneakEvent;
import de.hglabor.kitapi.kit.kits.NinjaKit;

import java.util.HashSet;
import java.util.Set;

public final class KitApi {
    public static final Set<AbstractKit> KIT_REGISTRY = new HashSet<>(); //TODO rename

    static {
        KIT_REGISTRY.add(NinjaKit.INSTANCE);
        for (AbstractKit kit : KIT_REGISTRY) {
            KitEventManager.register(kit);
        }
        KitEventManager.call(new KitPlayerToggleSneakEvent(null, true));
    }
}
