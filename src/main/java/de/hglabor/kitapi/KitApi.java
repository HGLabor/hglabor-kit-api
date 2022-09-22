package de.hglabor.kitapi;

import de.hglabor.kitapi.kit.kits.NoneKit;
import de.hglabor.kitapi.registry.BuiltinRegistries;
import de.hglabor.kitapi.registry.Identifier;

public class KitApi {
    public static void main(String[] args) {
        BuiltinRegistries.KIT_REGISTRY.register(new Identifier("none"), new NoneKit());
    }
}
