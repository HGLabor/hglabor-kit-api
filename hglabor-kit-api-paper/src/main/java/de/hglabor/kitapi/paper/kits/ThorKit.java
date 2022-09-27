package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ThorKit extends AbstractKit implements ISingleKitItem, Listener {
    public static final ThorKit INSTANCE = new ThorKit();
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.WOODEN_AXE).makeUnbreakable().build();
    public float cooldown = 5f;

    private ThorKit() {
        super("Thor");
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().getWorld().strikeLightningEffect(event.getPlayer().getLocation());
            applyCooldown(kitPlayer, cooldown);
        });
    }

    @Override
    public ItemStack getKitItem() {
        return KIT_ITEM;
    }
}
