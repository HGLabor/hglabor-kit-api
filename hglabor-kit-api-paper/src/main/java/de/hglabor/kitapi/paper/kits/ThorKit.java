package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ThorKit extends AbstractKit {
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
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM);
    }
}
