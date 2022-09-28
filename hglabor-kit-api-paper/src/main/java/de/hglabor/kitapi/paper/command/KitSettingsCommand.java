package de.hglabor.kitapi.paper.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.AbstractKit;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class KitSettingsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> kitsettings = literal("kitsettings");

        for (AbstractKit abstractKit : KitApi.getKits()) {
            LiteralArgumentBuilder<CommandSourceStack> kitNameLiteral = literal(abstractKit.getName());

            for (Field declaredField : abstractKit.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(declaredField.getModifiers())) continue;
                if (Modifier.isTransient(declaredField.getModifiers())) continue;

                LiteralArgumentBuilder<CommandSourceStack> fieldLiteral = literal(declaredField.getName());
                declaredField.setAccessible(true);

                fieldLiteral.executes(context -> {
                    try {
                        Object object = declaredField.get(abstractKit);
                        context.getSource().getBukkitSender().sendMessage("Aktueller Wert für " + declaredField.getName() + " ist: " + object.toString());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return 1;
                });

                for (SupportedArgument argument : SupportedArgument.values()) {
                    if (declaredField.getType().equals(argument.clazz) || Enum.class.isAssignableFrom(declaredField.getType())) {
                        RequiredArgumentBuilder<CommandSourceStack, ?> argumentBuilder = argument("value", argument.argumentType).executes(context -> {
                            Object value;
                            if (argument.getter != null) {
                                String input = StringArgumentType.getString(context, "value");
                                value = argument.getter.apply(input, declaredField.getType());
                            } else {
                                value = context.getArgument("value", argument.clazz);
                            }
                            try {
                                declaredField.set(abstractKit, value);
                                context.getSource().getBukkitSender().sendMessage(declaredField.getName() + " wurde auf " + value.toString() + " gesetzt.");
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                            return 1;
                        });
                        if (argument.suggestionProvider != null) {
                            System.out.println(declaredField.getType());
                            System.out.println(Enum.class.isAssignableFrom(declaredField.getType()));
                            argumentBuilder.suggests(argument.suggestionProvider.apply(declaredField.getType()));
                        }
                        fieldLiteral.then(argumentBuilder);
                    }
                }
                kitNameLiteral.then(fieldLiteral);
            }
            kitsettings.then(kitNameLiteral);
        }

        dispatcher.register(kitsettings);
    }

    private enum SupportedArgument {
        BOOLEAN(boolean.class, BoolArgumentType.bool()),
        DOUBLE(double.class, DoubleArgumentType.doubleArg()),
        INT(int.class, IntegerArgumentType.integer()),
        FLOAT(float.class, FloatArgumentType.floatArg()),
        LONG(long.class, LongArgumentType.longArg()),
        POTION_EFFECT_TYPE(PotionEffectType.class, StringArgumentType.string(), (s, clazz) -> {
            return PotionEffectType.getByName(s);
        }, (clazz) -> (context, builder) -> {
            System.out.println("###HÄÄÄ");
            for (PotionEffectType value : PotionEffectType.values()) {
                builder.suggest(value.getName());
            }
            return builder.buildFuture();
        }),
        @SuppressWarnings({"unchecked", "rawtypes"})
        ENUM(Enum.class, StringArgumentType.string(), (s, clazz) -> {
            return Enum.valueOf(((Class) clazz), s);
        }, (clazz) -> {
            System.out.println("###" + clazz);
            return (context, builder) -> {
                System.out.println("Ja grüß dich");
                EnumSet<? extends Enum<?>> values = EnumSet.allOf((Class) clazz);
                for (Enum<? extends Enum<?>> entry : values) {
                    System.out.println("###" + entry.name());
                    builder.suggest(entry.name());
                }
                return builder.buildFuture();
            };
        });
        private final Class<?> clazz;
        private final ArgumentType<?> argumentType;
        private final BiFunction<String, Class<?>, ?> getter;
        private final Function<Class<?>, SuggestionProvider<CommandSourceStack>> suggestionProvider;

        SupportedArgument(Class<?> clazz, ArgumentType<?> argumentType, BiFunction<String, Class<?>, ?> getter, Function<Class<?>, SuggestionProvider<CommandSourceStack>> suggestionProvider) {
            this.clazz = clazz;
            this.argumentType = argumentType;
            this.getter = getter;
            this.suggestionProvider = suggestionProvider;
        }

        SupportedArgument(Class<?> clazz, ArgumentType<?> argumentType) {
            this(clazz, argumentType, null, null);
        }
    }
}
