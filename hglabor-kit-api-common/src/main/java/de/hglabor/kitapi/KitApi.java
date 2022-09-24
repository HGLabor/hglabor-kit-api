package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.KitEventManager;
import de.hglabor.kitapi.kit.kits.NinjaKit;
import de.hglabor.kitapi.kit.kits.ThorKit;

import java.util.HashSet;
import java.util.Set;

public final class KitApi {
    private static final Set<AbstractKit> KIT_REGISTRY = new HashSet<>();

    public static void init() {
        KIT_REGISTRY.add(NinjaKit.INSTANCE);
        KIT_REGISTRY.add(ThorKit.INSTANCE);
        for (AbstractKit kit : KIT_REGISTRY) {
            System.out.println("Registered Kit " + kit.getName());
            KitEventManager.register(kit);
        }
    }
}
