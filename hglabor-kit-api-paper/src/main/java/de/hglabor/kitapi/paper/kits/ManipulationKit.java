package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ManipulationKit extends AbstractKit {
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
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM);
    }
}
