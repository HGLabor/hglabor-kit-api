package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

public class DiggerKit extends AbstractKit implements ISingleKitItem {
    private int radius = 6;
    private float cooldown = 12f;
    private int maxUsage = 3;

    public DiggerKit() {
        super("Digger");
        onKitItemPlace((event, kitPlayer) -> {
            Block block = event.getBlock();
            block.setType(Material.AIR);
            applyCooldown(kitPlayer, cooldown, maxUsage);
            runTaskLater(() -> {
                int dist = (int) Math.ceil((double) (radius - 1) / 2);
                for (int y = -1; y >= -radius; y--) {
                    for (int x = -dist; x <= dist; x++) {
                        for (int z = -dist; z <= dist; z++) {
                            if (block.getY() + y <= 0) {
                                continue;
                            }
                            Block b = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);
                            if (!b.getType().equals(Material.BEDROCK)) {
                                if (b instanceof Container) {
                                    b.breakNaturally();
                                } else {
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }, 15L);
        });
    }

    @Override
    public ItemStack getKitItem() {
        return new KitItemBuilder(Material.DRAGON_EGG).withAmount(16).build();
    }
}
