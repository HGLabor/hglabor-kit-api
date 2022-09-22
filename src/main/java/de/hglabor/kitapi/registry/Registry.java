package de.hglabor.kitapi.registry;

import java.util.List;

public interface Registry<E> {

    E get(Identifier identifier);

    void register(Identifier identifier, E element);

    Identifier getId(E element);

    List<E> getElements();
}
