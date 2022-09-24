package de.hglabor.kitapi.kit.event;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancel);
}
