package momo.dev.desiderata.mixin;

import momo.dev.desiderata.common.registry.DCoreTags;
import net.hecco.bountifulfares.definition.block.custom.HangingFruitBlock;
import net.hecco.bountifulfares.definition.block.custom.HangingHoaryAppleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingHoaryAppleBlock.class)
public class HangingHoaryAppleMixin extends HangingFruitBlock {
    public HangingHoaryAppleMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    public void nameless$canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                (Block.canSupportCenter(world, pos.above(), Direction.DOWN) || world.getBlockState(pos.above()).is(DCoreTags.CanHangOn.HOARY_APPLE)) && !world.isWaterAt(pos)
        );
    }
}
