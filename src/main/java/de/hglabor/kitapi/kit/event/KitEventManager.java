package de.hglabor.kitapi.kit.event;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.event.player.KitPlayerEvent;
import de.hglabor.kitapi.kit.player.IKitPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class KitEventManager {
    private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();

    private KitEventManager() {
    }

    public static void register(AbstractKit kit) {
        for (final Method method : kit.getClass().getDeclaredMethods()) {
            if (isMethodBad(method)) {
                continue;
            }

            register(method, kit);
        }
    }

    private static void register(Method method, AbstractKit kit) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        //New MethodData from the Method we are registering.
        KitEventHandler annotation = method.getAnnotation(KitEventHandler.class);
        final MethodData data = new MethodData(kit, method, annotation.priority(), annotation.ignoreCooldown(), annotation.ignoreCancelled(), annotation.sendCooldownMessage(), annotation.cooldownKey());

        //Set's the method to accessible so that we can also invoke it if it's protected or private.
        if (!data.target().isAccessible()) {
            data.target().setAccessible(true);
        }

        if (REGISTRY_MAP.containsKey(indexClass)) {
            if (!REGISTRY_MAP.get(indexClass).contains(data)) {
                REGISTRY_MAP.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        } else {
            REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<>() {
                {
                    add(data);
                }
            });
        }
    }

    public static void call(final KitPlayerEvent event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());
        if (dataList == null) return;

        IKitPlayer kitPlayer = event.getKitPlayer();

        if (event instanceof Cancellable cancellable) {
            for (final MethodData data : dataList) {
                if (cancellable.isCancelled() && data.ignoreCancelled()) {
                    break;
                }
                invoke(data, event);
            }
        } else {
            for (final MethodData data : dataList) {
                if (!data.kit().isEnabled()) continue;
                if (!kitPlayer.hasKit(data.kit())) continue;
                if (!data.ignoreCooldown()) {
                    if (data.kit() instanceof ISingleCooldown singleCooldown) {
                        if (kitPlayer.hasCooldown(singleCooldown)) {
                            if (data.sendCooldownMessage()) {
                                //TODO send cooldownMessage
                            }
                            continue;
                        }
                    } else if (data.kit() instanceof IMultiCooldown multiCooldown) {
                        if (kitPlayer.hasCooldown(multiCooldown, data.cooldownKey())) {
                            if (data.sendCooldownMessage()) {
                                //TODO send cooldownMessage
                            }
                            continue;
                        }
                    }
                }
                invoke(data, event);
            }
        }
    }

    private static void invoke(MethodData data, Event argument) {
        try {
            data.target().invoke(data.kit(), argument);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    private static void sortListValue(Class<? extends Event> indexClass) {
        List<MethodData> sortedList = new CopyOnWriteArrayList<>();

        for (final EventPriority priority : EventPriority.values()) {
            for (final MethodData data : REGISTRY_MAP.get(indexClass)) {
                if (data.priority() == priority) {
                    sortedList.add(data);
                }
            }
        }

        //Overwriting the existing entry.
        REGISTRY_MAP.put(indexClass, sortedList);
    }

    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(KitEventHandler.class);
    }

    private record MethodData(AbstractKit kit, Method target, EventPriority priority, boolean ignoreCooldown,
                              boolean ignoreCancelled, boolean sendCooldownMessage, String cooldownKey) {
    }
}
