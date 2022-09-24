package de.hglabor.kitapi.kit.event.player;

import de.hglabor.kitapi.kit.event.Action;
import de.hglabor.kitapi.kit.player.IKitPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class KitItemInteractEvent extends KitPlayerEvent {
    protected final ItemStack item;
    protected final Action action;
    protected final Block blockClicked;
    private final Result useClickedBlock;
    private final Result useItemInHand;
    private final EquipmentSlot hand;

    public KitItemInteractEvent(@NotNull IKitPlayer who, ItemStack item, Action action, Block blockClicked, Result useClickedBlock, Result useItemInHand, EquipmentSlot hand) {
        super(who);
        this.item = item;
        this.action = action;
        this.blockClicked = blockClicked;
        this.useClickedBlock = useClickedBlock;
        this.useItemInHand = useItemInHand;
        this.hand = hand;
    }

    public ItemStack getItem() {
        return item;
    }

    public Action getAction() {
        return action;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }

    public Result getUseClickedBlock() {
        return useClickedBlock;
    }

    public Result getUseItemInHand() {
        return useItemInHand;
    }

    public EquipmentSlot getHand() {
        return hand;
    }
}
