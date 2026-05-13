package momo.dev.yonder.mixin.bountifulfares;

import com.mojang.serialization.MapCodec;
import momo.dev.yonder.common.registry.YonderTags;
import net.hecco.bountifulfares.definition.block.custom.HangingWalnutsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingWalnutsBlock.class)
public class HangingWalnutsBlockMixin extends FallingBlock {
    public HangingWalnutsBlockMixin(Properties properties) {
        super(properties);
    }

    @Override protected MapCodec<? extends FallingBlock> codec() { return null; }

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    public void yonder$canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                world.getBlockState(pos.above()).is(YonderTags.Blocks.CAN_HANG_ON.WALNUT) && !world.isWaterAt(pos)
        );
    }
}
