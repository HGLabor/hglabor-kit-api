package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.KitEventManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class KitApi {
    private static final Set<AbstractKit> KIT_REGISTRY = new HashSet<>();
    private static JavaPlugin plugin;

    public static void init() {
        for (AbstractKit kit : KIT_REGISTRY) {
            System.out.println("Registered Kit " + kit.getName());
            KitEventManager.register(kit);
        }
    }

    public static boolean register(AbstractKit kit) {
        return KIT_REGISTRY.add(kit);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
