package de.hglabor.kitapi.kit.event.player;

import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.jetbrains.annotations.NotNull;

public class KitPlayerToggleSneakEvent<Player> extends KitPlayerEvent<Player> /*implements Cancellable*/ {
    private final boolean isSneaking;
    private boolean cancel = false;

    public KitPlayerToggleSneakEvent(@NotNull final IKitPlayer player, final boolean isSneaking) {
        super(player);
        this.isSneaking = isSneaking;
    }

    public boolean isSneaking() {
        return isSneaking;
    }

    //@Override
    public boolean isCancelled() {
        return cancel;
    }

    //@Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}