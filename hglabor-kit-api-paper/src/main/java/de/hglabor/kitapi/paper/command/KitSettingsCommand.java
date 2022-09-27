package de.hglabor.kitapi.paper.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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
import java.util.function.Function;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class KitSettingsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> kitsettings = literal("kitsettings");

        for (AbstractKit abstractKit : KitApi.KIT_REGISTRY) {
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
                    if (declaredField.getType().equals(argument.clazz)) {
                        RequiredArgumentBuilder<CommandSourceStack, ?> argumentBuilder = argument("value", argument.argumentType).executes(context -> {
                            Object value;
                            if (argument.getter != null) {
                                String input = StringArgumentType.getString(context, "value");
                                value = argument.getter.apply(input);
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
                            argumentBuilder.suggests(argument.suggestionProvider);
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
        ENTITY_TYPE(EntityType.class, StringArgumentType.string(), EntityType::valueOf, (context, builder) -> {
            for (EntityType value : EntityType.values()) {
                builder.suggest(value.name());
            }
            return builder.buildFuture();
        }),
        PARTICLE(Particle.class, StringArgumentType.string(), Particle::valueOf, (context, builder) -> {
            for (Particle value : Particle.values()) {
                builder.suggest(value.name());
            }
            return builder.buildFuture();
        }),
        POTION_EFFECT_TYPE(PotionEffectType.class, StringArgumentType.string(), PotionEffectType::getByName, (context, builder) -> {
            for (PotionEffectType value : PotionEffectType.values()) {
                builder.suggest(value.getName());
            }
            return builder.buildFuture();
        }),
        MATERIAL(Material.class, StringArgumentType.string(), Material::valueOf, (context, builder) -> {
            for (Material value : Material.values()) {
                builder.suggest(value.name());
            }
            return builder.buildFuture();
        });
        private final Class<?> clazz;
        private final ArgumentType<?> argumentType;
        private final Function<String, ?> getter;
        private final SuggestionProvider<CommandSourceStack> suggestionProvider;

        SupportedArgument(Class<?> clazz, ArgumentType<?> argumentType, Function<String, ?> getter, SuggestionProvider<CommandSourceStack> suggestionProvider) {
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
