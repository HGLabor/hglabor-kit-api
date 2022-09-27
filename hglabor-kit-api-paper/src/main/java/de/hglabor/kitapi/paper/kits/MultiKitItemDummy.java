package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.IMultiKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MultiKitItemDummy extends AbstractKit implements IMultiKitItem {
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.IRON_INGOT).makeUnbreakable().build();
    private static final ItemStack KIT_ITEM_2 = new KitItemBuilder(Material.GOLD_INGOT).makeUnbreakable().build();
    public static final MultiKitItemDummy INSTANCE = new MultiKitItemDummy();
    private float goldCooldown = 5f;
    private float ironCooldown = 5f;
    private float cooldown = 5f;

    protected MultiKitItemDummy() {
        super("MultiKitDummy");
        onKitItemLeftClick((event, kitPlayer) -> {
            this.applyCooldown(kitPlayer, cooldown);
        }, KIT_ITEM);
        onKitItemLeftClickAtEntity(this::test, KIT_ITEM);
    }

    public void test(EntityDamageByEntityEvent event, IKitPlayer kitPlayer) {
        event.getEntity().setGlowing(!event.getEntity().isGlowing());
        event.getEntity().setGravity(false);
        event.getEntity().addPassenger(kitPlayer.getPlayer().orElseThrow());
    }

    @Override
    public List<ItemStack> getKitItems() {
        return List.of(KIT_ITEM, KIT_ITEM_2);
    }
}
