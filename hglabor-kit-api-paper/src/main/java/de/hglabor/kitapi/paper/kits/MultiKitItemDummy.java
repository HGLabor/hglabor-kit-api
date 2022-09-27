package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.IMultiCooldown;
import de.hglabor.kitapi.kit.item.IMultiKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class MultiKitItemDummy extends AbstractKit implements IMultiKitItem, IMultiCooldown {
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.IRON_INGOT).makeUnbreakable().build();
    private static final ItemStack KIT_ITEM_2 = new KitItemBuilder(Material.GOLD_INGOT).makeUnbreakable().build();
    public static final MultiKitItemDummy INSTANCE = new MultiKitItemDummy();
    private float goldCooldown = 5f;
    private float ironCooldown = 5f;

    protected MultiKitItemDummy() {
        super("MultiKitDummy");
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().sendMessage("RightClicked " + event.getItem().getType());
            kitPlayer.addCooldown(this, "Iron");
        }, KIT_ITEM, "Iron");
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().sendMessage("RightClicked " + event.getItem().getType());
            kitPlayer.addCooldown(this, "Gold");
        }, KIT_ITEM_2, "Gold");
        onKitItemLeftClick((event, kitPlayer) -> {
            event.getPlayer().sendMessage("LeftClicked " + event.getItem().getType());
        }, KIT_ITEM);
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM, KIT_ITEM_2);
    }

    @Override
    public Map<String, Float> getCooldowns() {
        return Map.of("Iron", ironCooldown, "Gold", goldCooldown);
    }
}
