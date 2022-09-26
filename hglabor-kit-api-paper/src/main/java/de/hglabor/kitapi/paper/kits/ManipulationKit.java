package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

public class ManipulationKit extends AbstractKit implements ISingleKitItem {
    public static final ManipulationKit INSTANCE = new ManipulationKit();
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.IRON_NUGGET).build();

    private ManipulationKit() {
        super("Manipulation");
        onKitItemRightClickAtEntity((event, kitPlayer) -> {
            if (event.getRightClicked() instanceof Mob mob) {
                event.getPlayer().sendMessage(mob.getType().name());
                mob.remove();
            }
        });
    }

    @Override
    public ItemStack getKitItem() {
        return KIT_ITEM;
    }
}
