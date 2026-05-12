package momo.dev.yonder.mixin.bountifulfares;

import momo.dev.yonder.common.registry.YonderTags;
import net.hecco.bountifulfares.definition.block.custom.HangingFruitBlock;
import net.hecco.bountifulfares.definition.block.custom.HangingHoaryAppleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingHoaryAppleBlock.class)
public class HangingHoaryAppleBlockMixin extends HangingFruitBlock {
    public HangingHoaryAppleBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    public void yonder$canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                world.getBlockState(pos.above()).is(YonderTags.Blocks.CanHangOn.HOARY_APPLE) && !world.isWaterAt(pos)
        );
    }
}
