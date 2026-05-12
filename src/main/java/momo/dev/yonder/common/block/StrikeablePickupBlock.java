package momo.dev.yonder.common.block;

import com.farcr.nomansland.common.block.GroundPickupBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class StrikeablePickupBlock extends GroundPickupBlock {
    private final Supplier<ItemStack> strikeItem;

    public StrikeablePickupBlock(Supplier<ItemStack> strikeItem, Properties properties) {
        super(properties);
        this.strikeItem = strikeItem;
    }

    @Override protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(strikeItem.get().getItem())) {
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }
}
