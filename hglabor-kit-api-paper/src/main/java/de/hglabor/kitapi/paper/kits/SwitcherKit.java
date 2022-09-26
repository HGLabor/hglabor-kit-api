package de.hglabor.kitapi.paper.kits;

import de.hglabor.kitapi.KitApi;
import de.hglabor.kitapi.kit.AbstractKit;
import de.hglabor.kitapi.kit.cooldown.ISingleCooldown;
import de.hglabor.kitapi.kit.item.ISingleKitItem;
import de.hglabor.kitapi.kit.item.KitItemBuilder;
import de.hglabor.kitapi.kit.util.EventUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class SwitcherKit extends AbstractKit implements ISingleCooldown, ISingleKitItem {
    public final static SwitcherKit INSTANCE = new SwitcherKit();
    private static final ItemStack KIT_ITEM = new KitItemBuilder(Material.SNOWBALL).withAmount(16).build();
    private final String MARKER = "switcher-projectile";
    private final EntityType projectileType = EntityType.SNOWBALL;
    private float cooldown = 5;

    private SwitcherKit() {
        super("Switcher");
        onKitItemRightClick((event, kitPlayer) -> {
            event.getPlayer().launchProjectile(Snowball.class).setMetadata(MARKER, new FixedMetadataValue(KitApi.getPlugin(), null));
        });
        playerKitEvent(ProjectileHitEvent.class, EventUtils::getShooter, (event, kitPlayer) -> {
            if (event.getHitEntity() != null && event.getEntity().hasMetadata(MARKER)) {
                Entity hitEntity = event.getHitEntity();
                Player shooter = kitPlayer.getPlayer().orElseThrow();
                Location hitLocation = hitEntity.getLocation().clone();
                Location shooterLocation = shooter.getLocation().clone();
                hitEntity.teleport(shooterLocation);
                shooter.teleport(hitLocation);
                kitPlayer.addCooldown(this);
            }
        });
    }

    @Override
    public float getCooldown() {
        return cooldown;
    }

    @Override
    public ItemStack getKitItem() {
        return KIT_ITEM;
    }
}
