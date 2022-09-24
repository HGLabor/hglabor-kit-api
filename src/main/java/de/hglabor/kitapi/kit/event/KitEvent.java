package de.hglabor.kitapi.kit.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KitEvent {
    boolean ignoreCooldown() default false;

    boolean ignoreCancelled() default false;

    boolean sendCooldownMessage() default true;

    String[] cooldownKeys() default {};
}
