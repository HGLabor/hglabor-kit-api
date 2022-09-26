package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.IMultiKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MultiKitItemDummy extends AbstractKit implements IMultiKitItem {
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.IRON_INGOT).makeUnbreakable().build();
    private static final ItemStack KIT_ITEM_2 = new KitItemBuilder(Material.GOLD_INGOT).makeUnbreakable().build();
    public static final MultiKitItemDummy INSTANCE = new MultiKitItemDummy();

    protected MultiKitItemDummy() {
        super("MultiKitDummy");
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().sendMessage("Clicked " + event.getItem().getType());
        }, KIT_ITEM);
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().sendMessage("Clicked " + event.getItem().getType());
        }, KIT_ITEM_2);
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM, KIT_ITEM_2);
    }
}
