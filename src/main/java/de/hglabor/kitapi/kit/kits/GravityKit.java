package de.hglabor.kitapi.kit.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GravityKit extends AbstractKit implements ISingleKitItem, ISingleCooldown {

    public void test() {

    }

    @Override
    public float getCooldown() {
        return 1.5f;
    }

    @Override
    public ItemStack getKitItem() {
        return Items.MAGENTA_GLAZED_TERRACOTTA.getDefaultInstance();
    }
}
