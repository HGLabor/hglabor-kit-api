package de.hglabor.kitapi.kit.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.event.KitEventHandler;
import de.hglabor.kitapi.kit.event.player.KitItemInteractEvent;
import de.hglabor.kitapi.kit.item.IKitItemStack;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Items;

public class ThorKit extends AbstractKit implements ISingleKitItem, ISingleCooldown {
    public static final ThorKit INSTANCE = new ThorKit();
    private static final IKitItemStack KIT_ITEM = new KitItem(Items.WOODEN_AXE);

    private ThorKit() {
        super("Thor");
    }

    @KitEventHandler
    public void onKitItemInteract(KitItemInteractEvent event) {
        event.getKitPlayer().getPlayer().ifPresent(player -> {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.getLevel());
            lightningBolt.teleportTo(player.getX(), player.getY(), player.getZ());
            lightningBolt.setVisualOnly(true);
            player.getLevel().addFreshEntity(lightningBolt);
        });
        event.getKitPlayer().addCooldown(this);
    }

    @Override
    public IKitItemStack getKitItem() {
        return KIT_ITEM;
    }

    @Override
    public float getCooldown() {
        return 5f;
    }
}
