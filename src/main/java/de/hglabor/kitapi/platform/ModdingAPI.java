package de.hglabor.kitapi.platform;

public enum ModdingAPI {
    /**
     * No modding API is in use and just normal minecraft-classes are used
     */
    NONE,
    /**
     * The Paper modding API is in use and is required on the server
     */
    PAPER,
    /**
     * Mixins are being used to modify classed and a mixin-supporting loader is required on the server
     */
    MIXIN,
    /**
     * The Fabric modding API is in use and is required on the server
     */
    FABRIC,
    /**
     * The Quilt modding API is in use and is required on the server
     */
    QUILT
}
