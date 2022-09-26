package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@ApiStatus.Internal
public final class KitApi {
    public static final Set<AbstractKit> KIT_REGISTRY = new HashSet<>();
    private static JavaPlugin plugin;
    private static Function<UUID, IKitPlayer> playerGetter;

    public static void init(Function<UUID,IKitPlayer> playerGetter, JavaPlugin plugin) {
        KitApi.plugin = plugin;
        KitApi.playerGetter = playerGetter;
    }

    public static boolean register(AbstractKit kit) {
        return KIT_REGISTRY.add(kit);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    public static IKitPlayer getKitPlayer(UUID uuid) {
        return playerGetter.apply(uuid);
    }
}
