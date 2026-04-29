package momo.dev.desiderata.mixin;

import com.mojang.serialization.MapCodec;
import momo.dev.desiderata.common.registry.DCoreTags;
import net.hecco.bountifulfares.definition.block.custom.HangingWalnutsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingWalnutsBlock.class)
public class HangingWalnutsMixin extends FallingBlock {
    public HangingWalnutsMixin(Properties properties) {
        super(properties);
    }

    @Overwrite protected MapCodec<? extends FallingBlock> codec() { return null; }

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    public void nameless$canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                Block.canSupportCenter(world, pos.above(), Direction.DOWN) && !world.isWaterAt(pos)
                || world.getBlockState(pos.above()).is(DCoreTags.CanHangOn.WALNUT) && !world.isWaterAt(pos)
        );
    }
}
