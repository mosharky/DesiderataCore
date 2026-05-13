package momo.dev.yonder.common.registry.block;

import com.farcr.nomansland.common.registry.items.NMLItems;
import momo.dev.yonder.common.registry.YonderTags;
import net.hecco.bountifulfares.definition.block.custom.HangingFruitBlock;
import net.hecco.bountifulfares.definition.platform.Services;
import net.hecco.bountifulfares.definition.trigger.PickFruitInteractionTrigger;
import net.hecco.bountifulfares.registry.content.BFSounds;
import net.hecco.bountifulfares.registry.misc.BFCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingPearBlock extends HangingFruitBlock {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(7, 13, 7, 9, 16, 9),
            Block.box(6, 13, 6, 10, 16, 10),
            Block.box(6.5, 13, 6.5, 9.5, 16, 9.5),
            Shapes.join(Block.box(5.5, 10, 5.5, 10.5, 15, 10.5), Block.box(7, 15, 7, 9, 16, 9), BooleanOp.OR),
            Shapes.join(Block.box(5, 8, 5, 11, 14, 11), Block.box(7, 14, 7, 9, 16, 9), BooleanOp.OR)};
    private static final VoxelShape[] COLL_SHAPES = new VoxelShape[]{Shapes.empty(),
            Shapes.empty(),
            Block.box(6.5, 13, 6.5, 9.5, 16, 9.5),
            Shapes.join(Block.box(5.5, 10, 5.5, 10.5, 15, 10.5), Block.box(7, 15, 7, 9, 16, 9), BooleanOp.OR),
            Shapes.join(Block.box(5, 8, 5, 11, 14, 11), Block.box(7, 14, 7, 9, 16, 9), BooleanOp.OR)};

    public HangingPearBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape voxelShape = SHAPES[state.getValue(AGE)];
        return voxelShape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape voxelShape = COLL_SHAPES[state.getValue(AGE)];
        return voxelShape;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return (world.getBlockState(pos.above()).is(YonderTags.Blocks.CanHangOn.PEAR)) && !world.isWaterAt(pos);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        int i = state.getValue(AGE);
        if (i == 4) {
            HangingFruitBlock.popResource(world, pos, new ItemStack(NMLItems.PEAR.get(), 1));
            world.playSound(null, pos, BFSounds.HANGING_FRUIT_PICK.get(), SoundSource.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            if (!world.isClientSide()) {
                if (Services.PLATFORM.get().getBoolConfigValue("fruitReplaceWhenPicked")) {
                    BlockState blockState = state.setValue(AGE, 0);
                    world.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
                    world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
                } else {
                    world.removeBlock(pos, false);
                }
                ((PickFruitInteractionTrigger) BFCriteriaTriggers.PICK_FRUIT.get()).trigger((ServerPlayer) player, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return new ItemStack(NMLItems.PEAR.get());
    }
}
