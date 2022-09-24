package de.hglabor.kitapi.kit.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.event.KitEventHandler;
import de.hglabor.kitapi.kit.event.player.KitPlayerToggleSneakEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NinjaKit extends AbstractKit {
    public static final NinjaKit INSTANCE = new NinjaKit();

    private NinjaKit() {
        super("Ninja");
    }

    @KitEventHandler
    public void onToggleSneak(KitPlayerToggleSneakEvent toggleSneakEvent) {
        if (toggleSneakEvent.isSneaking()) {
            toggleSneakEvent.getKitPlayer().getPlayer().ifPresent(player -> {
                player.teleportTo(player.getX(), player.getY() + 5, player.getZ());
            });
        }
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack(Items.ALLIUM).setHoverName(new TextComponent("Ninja Override"));
    }
}
