package de.hglabor.kitapi.kit.event.player;

import de.hglabor.kitapi.kit.event.Event;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class KitPlayerEvent<Player> extends Event {
    protected IKitPlayer kitPlayer;

    public KitPlayerEvent(@NotNull final IKitPlayer who) {
        kitPlayer = who;
    }

    public KitPlayerEvent(@NotNull final IKitPlayer who, boolean async) {
        super(async);
        kitPlayer = who;
    }

    @NotNull
    public final IKitPlayer getKitPlayer() {
        return kitPlayer;
    }
}
