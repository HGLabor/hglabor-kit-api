package de.hglabor.kitapi.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRegistry<E> implements Registry<E> {
    private Map<Identifier, E> map = new HashMap<>();

    @Override
    public E get(Identifier identifier) {
        return map.get(identifier);
    }

    @Override
    public void register(Identifier identifier, E element) {
        if (map.containsKey(identifier)) {
            throw new RuntimeException("Element cannot be registered twice");
        } else {
            map.put(identifier, element);
        }
    }

    @Override
    public Identifier getId(E element) {
        return map.entrySet().stream().filter(it -> it.getValue() == element).findFirst().orElseThrow().getKey();
    }

    @Override
    public List<E> getElements() {
        return map.values().stream().toList();
    }
}
