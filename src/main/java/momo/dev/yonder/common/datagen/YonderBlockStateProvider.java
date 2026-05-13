package momo.dev.yonder.common.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import momo.dev.yonder.Yonder;
import net.hecco.bountifulfares.BountifulFares;
import net.hecco.bountifulfares.definition.block.custom.FruitLogBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

import static momo.dev.yonder.common.registry.YonderBlocks.*;

public class YonderBlockStateProvider extends BlockStateProvider {
    public YonderBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Yonder.MODID, existingFileHelper);
    }

    @Override protected void registerStatesAndModels() {
        // Flint Pebbles
        this.simpleBlockWithVariation(FLINT_PEBBLES.get(),
                (i) -> {
                    int index = i + 1;
                    String name = name(FLINT_PEBBLES);
                    return this.models().getBuilder(name + "_" + index)
                            .parent(new ModelFile.UncheckedModelFile(NoMansLand.location("block/pebble/pebble_" + index)))
                            .texture("0", modLoc(ModelProvider.BLOCK_FOLDER + "/" + name));
                },11);
        this.flatBlockItem(FLINT_PEBBLES.get(), modLoc("item/flint_pebbles"));

        // Leaves
        simpleBlockWithItem(DEAD_LEAVES.get(), models().leaves(name(DEAD_LEAVES), modLoc("block/dead_leaves")));
        simpleBlockWithItem(ASPEN_LEAVES.get(), models().leaves(name(ASPEN_LEAVES), modLoc("block/aspen_leaves")));
        simpleBlockWithItem(BIRCH_LEAVES.get(), models().leaves(name(BIRCH_LEAVES), modLoc("block/birch_leaves")));
        fruitLeaves(PEAR_LEAVES.get(), FLOWERING_PEAR_LEAVES.get());

        // Logs
        fruitLogModels(ASPEN_LOG.get(), ASPEN_WOOD.get(), ASPEN_LEAVES.get());
        fruitLogModels(STRIPPED_ASPEN_LOG.get(), STRIPPED_ASPEN_WOOD.get());
        fruitLogModels(BIRCH_LOG.get(), BIRCH_WOOD.get(), BIRCH_LEAVES.get());
        fruitLogModels(STRIPPED_BIRCH_LOG.get(), STRIPPED_BIRCH_WOOD.get());
        fruitLogModels(PEAR_LOG.get(), PEAR_WOOD.get(), PEAR_LEAVES.get());
        fruitLogModels(STRIPPED_PEAR_LOG.get(), STRIPPED_PEAR_WOOD.get());
        // Saplings
        sapling(ASPEN_SAPLING.get(), POTTED_ASPEN_SAPLING.get());
        sapling(BIRCH_SAPLING.get(), POTTED_BIRCH_SAPLING.get());
        sapling(PEAR_SAPLING.get(), POTTED_PEAR_SAPLING.get());
    }

    public void sapling(Block sapling, Block pottedSapling) {
        simpleBlock(sapling, models().cross(name(sapling), modLoc("block/" + name(sapling)))
                .renderType(mcLoc("cutout"))
        );
        flatBlockItem(sapling);
        simpleBlock(pottedSapling, models().withExistingParent(name(pottedSapling), mcLoc("block/flower_pot_cross"))
                .texture("plant", modLoc("block/" + name(sapling)))
                .renderType(mcLoc("cutout"))
        );
    }

    public void fruitLeaves(Block leaves, Block floweringLeaves) {
        simpleBlockWithItem(leaves, models().leaves(name(leaves), modLoc("block/" + name(leaves))));
        simpleBlockWithItem(floweringLeaves, models()
                .withExistingParent(name(floweringLeaves), BountifulFares.id("block/leaves_with_overlay"))
                .texture("all", modLoc("block/" +  name(leaves)))
                .texture("overlay", modLoc("block/" +  name(floweringLeaves)))
        );
    }

    public void fruitLogModels(Block log, Block wood) {
        String logName = name(log);
        String woodName = name(wood);
        // templates
        ResourceLocation template_fruit_log = BountifulFares.id("block/template_fruit_log");
        ResourceLocation template_fruit_log_noside = BountifulFares.id("block/template_fruit_log_noside");
        ResourceLocation template_fruit_log_otherside = BountifulFares.id("block/template_fruit_log_otherside");
        ResourceLocation template_fruit_log_side = BountifulFares.id("block/template_fruit_log_side");
        ResourceLocation template_fruit_wood_otherside = BountifulFares.id("block/template_fruit_wood_otherside");
        ResourceLocation template_fruit_wood_side = BountifulFares.id("block/template_fruit_wood_side");
        // template-applied model files
        ModelFile fruit_log = models().withExistingParent(logName, template_fruit_log).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_noside = models().withExistingParent(logName + "_noside", template_fruit_log_noside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_otherside = models().withExistingParent(logName + "_otherside", template_fruit_log_otherside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_side = models().withExistingParent(logName + "_side", template_fruit_log_side).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_wood_otherside = models().withExistingParent(woodName + "_otherside", template_fruit_wood_otherside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_wood_side = models().withExistingParent(woodName + "_side", template_fruit_wood_side).texture("texture", modLoc("block/" + logName));

        // item models
        itemModels().withExistingParent(logName, BountifulFares.id("item/template_fruit_log")).texture("texture", modLoc("block/" + logName));
        itemModels().withExistingParent(woodName, BountifulFares.id("item/template_fruit_wood")).texture("texture", modLoc("block/" + logName));

        // block models
        getMultipartBuilder(log)
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.NORTH, true).end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.EAST, true).end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.SOUTH, true).end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.WEST, true).end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.UP, true).end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.DOWN, true).end()
            .part()
                .modelFile(fruit_log).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y).end()
            .part()
                .modelFile(fruit_log_noside).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, true)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, true)
                .end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end();

        // wood blockstate
    getMultipartBuilder(wood)
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.NORTH, true).end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.EAST, true).end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.SOUTH, true).end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.WEST, true).end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.UP, true).end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.DOWN, true).end()
            .part()
                .modelFile(fruit_log).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y).end()
            .part()
                .modelFile(fruit_log_noside).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, true)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, true)
                .end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end();
    }

    public void fruitLogModels(Block log, Block wood, Block leaves) {
        String logName = name(log);
        String woodName = name(wood);
        String leavesName = name(leaves);
        // templates
        ResourceLocation template_fruit_log = BountifulFares.id("block/template_fruit_log");
        ResourceLocation template_fruit_log_noside = BountifulFares.id("block/template_fruit_log_noside");
        ResourceLocation template_fruit_log_otherside = BountifulFares.id("block/template_fruit_log_otherside");
        ResourceLocation template_fruit_log_side = BountifulFares.id("block/template_fruit_log_side");
        ResourceLocation template_fruit_wood_otherside = BountifulFares.id("block/template_fruit_wood_otherside");
        ResourceLocation template_fruit_wood_side = BountifulFares.id("block/template_fruit_wood_side");
        // template-applied model files
        ModelFile fruit_log = models().withExistingParent(logName, template_fruit_log).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_noside = models().withExistingParent(logName + "_noside", template_fruit_log_noside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_otherside = models().withExistingParent(logName + "_otherside", template_fruit_log_otherside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_log_side = models().withExistingParent(logName + "_side", template_fruit_log_side).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_wood_otherside = models().withExistingParent(woodName + "_otherside", template_fruit_wood_otherside).texture("texture", modLoc("block/" + logName));
        ModelFile fruit_wood_side = models().withExistingParent(woodName + "_side", template_fruit_wood_side).texture("texture", modLoc("block/" + logName));

        // item models
        itemModels().withExistingParent(logName, BountifulFares.id("item/template_fruit_log")).texture("texture", modLoc("block/" + logName));
        itemModels().withExistingParent(woodName, BountifulFares.id("item/template_fruit_wood")).texture("texture", modLoc("block/" + logName));

        // block models
        getMultipartBuilder(log)
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.NORTH, true).end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.EAST, true).end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.SOUTH, true).end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.WEST, true).end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.UP, true).end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.DOWN, true).end()
            .part()
                .modelFile(fruit_log).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y).end()
            .part()
                .modelFile(fruit_log_noside).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, true)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, true)
                .end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_log_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(models().getExistingFile(modLoc("block/" + leavesName))).uvLock(true).addModel()
                .condition(FruitLogBlock.LEAFY, true)
                .end();

        // wood blockstate
        getMultipartBuilder(wood)
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.NORTH, true).end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.EAST, true).end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.SOUTH, true).end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.WEST, true).end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.UP, true).end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.DOWN, true).end()
            .part()
                .modelFile(fruit_log).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y).end()
            .part()
                .modelFile(fruit_log_noside).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, true)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, true)
                .end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Y)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(90).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(270).rotationY(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.X)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_log).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).rotationY(180).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .end()
            .part()
                .modelFile(fruit_log_noside).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_otherside).rotationX(90).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.NORTH, false)
                .condition(FruitLogBlock.SOUTH, false)
                .end()
            .part()
                .modelFile(fruit_wood_side).rotationX(270).addModel()
                .condition(FruitLogBlock.AXIS, Direction.Axis.Z)
                .condition(FruitLogBlock.DOWN, false)
                .condition(FruitLogBlock.EAST, false)
                .condition(FruitLogBlock.SOUTH, false)
                .condition(FruitLogBlock.UP, false)
                .condition(FruitLogBlock.WEST, false)
                .end()
            .part()
                .modelFile(models().getExistingFile(modLoc("block/" + leavesName))).uvLock(true).addModel()
                .condition(FruitLogBlock.LEAFY, true)
                .end();
    }


    // Helpers from NML
    public void flatBlockItem(Block block) {
        this.flatBlockItem(block, this.modLoc("block/" + name(block)));
    }

    public void flatBlockItem(Block block, ResourceLocation texture) {
        this.itemModels().getBuilder(key(block).getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    private void simpleBlockWithVariation(Block block, int variations) {
        String blockName = name(block);
        this.simpleBlockWithVariation(block, (i) -> {
            String name = blockName + "_" + i;
            return this.models().cubeAll(name, this.modLoc(ModelProvider.BLOCK_FOLDER + "/" + name));
        }, variations);
    }

    private void simpleBlockWithVariation(Block block, Function<Integer, ModelBuilder> modelFactory, int variations) {
        for (int i =0; i < variations; i++) {
            this.getVariantBuilder(block).partialState().addModels(ConfiguredModel.builder()
                    .modelFile(modelFactory.apply(i))
                    .buildLast()
            );
        }
    }

    private void simpleBlockWithVariationAndTransformation(Block block, int variations, boolean rotateX, boolean rotateY) {
        String blockName = name(block);
        this.simpleBlockWithVariationAndTransformation(
                block, (i) -> {
                    String suffix = "_" + i;
                    return this.models().cubeAll(
                            blockName + suffix,
                            this.modLoc(ModelProvider.BLOCK_FOLDER + "/" + blockName + suffix)
                    );
                }, (i) -> {
                    String suffix = "_" + i;
                    return this.models().singleTexture(
                            blockName + suffix + "_mirrored",
                            this.mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"),
                            "all",
                            this.modLoc(ModelProvider.BLOCK_FOLDER + "/" + blockName + suffix)
                    );
                },
                variations, rotateX, rotateY );
    }

    private void simpleBlockWithVariationAndTransformation(Block block, Function<Integer, ModelBuilder> modelFactory, Function<Integer, ModelBuilder> mirroredModelFactory, int variations, boolean rotateX, boolean rotateY) {
        ModelBuilder[] models = new ModelBuilder[variations *2];
        for (int i =0; i < variations; i++) {
            models[i *2] = modelFactory.apply(i);
            models[i *2 +1] = mirroredModelFactory.apply(i);
        }

        for (int i =0; i < variations; i++) {
            this.getVariantBuilder(block).partialState().addModels(
                    ConfiguredModel.builder().modelFile(models[i *2]).buildLast(),
                    ConfiguredModel.builder().modelFile(models[i *2 +1]).buildLast()
            );

            if (rotateX && rotateY) {
                for (int rotX =1; rotX <4; rotX++) {
                    for (int rotY =0; rotY <4; rotY++) {
                        this.getVariantBuilder(block).partialState().addModels(
                                ConfiguredModel.builder().modelFile(models[i *2]).rotationX(rotX *90).rotationY(rotY *90).buildLast(),
                                ConfiguredModel.builder().modelFile(models[i *2 +1]).rotationX(rotX *90).rotationY(rotY *90).buildLast()
                        );
                    }
                }
            } else if (rotateX) {
                for (int rot =1; rot <4; rot++) {
                    this.getVariantBuilder(block).partialState().addModels(
                            ConfiguredModel.builder().modelFile(models[i *2]).rotationX(rot *90).buildLast(),
                            ConfiguredModel.builder().modelFile(models[i *2 +1]).rotationX(rot *90).buildLast()
                    );
                }
            } else if (rotateY) {
                for (int rot =1; rot <4; rot++) {
                    this.getVariantBuilder(block).partialState().addModels(
                            ConfiguredModel.builder().modelFile(models[i *2]).rotationY(rot *90).buildLast(),
                            ConfiguredModel.builder().modelFile(models[i *2 +1]).rotationY(rot *90).buildLast()
                    );
                }
            }
        }
    }

    private void slabBlockWithVariation(Block block, Function<Integer, ResourceLocation> doubleSlabFactory, Function<Integer, ResourceLocation> texFactory, int variations) {
        this.slabBlockWithVariation(block, doubleSlabFactory, texFactory, texFactory, texFactory, variations);
    }

    private void slabBlockWithVariation(Block block,
                                        Function<Integer, ResourceLocation> doubleSlabFactory,
                                        Function<Integer, ResourceLocation> sideTexFactory,
                                        Function<Integer, ResourceLocation> bottomTexFactory,
                                        Function<Integer, ResourceLocation> topTexFactory,
                                        int variations) {
        String blockName = name(block);
        for (int i =0; i < variations; i++) {
            String suffix = "_" + i;
            ResourceLocation sideTex = sideTexFactory.apply(i);
            ResourceLocation bottomTex = bottomTexFactory.apply(i);
            ResourceLocation topTex = topTexFactory.apply(i);
            getVariantBuilder(block)
                    .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(this.models().getExistingFile(doubleSlabFactory.apply(i))))
                    .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(models().slab(blockName + suffix, sideTex, bottomTex, topTex)))
                    .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(models().slabTop(blockName + "_top" + suffix, sideTex, bottomTex, topTex)));
        }
    }

    private void stairsBlockWithVariation(Block block, Function<Integer, ResourceLocation> texFactory, int variations) {
        this.stairsBlockWithVariation(block, texFactory, texFactory, texFactory, variations);
    }

    private void stairsBlockWithVariation(Block block,
                                          Function<Integer, ResourceLocation> sideTexFactory,
                                          Function<Integer, ResourceLocation> bottomTexFactory,
                                          Function<Integer, ResourceLocation> topTexFactory,
                                          int variations) {
        ModelFile[] stairs = new ModelFile[variations];
        ModelFile[] stairsInner = new ModelFile[variations];
        ModelFile[] stairsOuter = new ModelFile[variations];

        String blockName = name(block);
        for (int i =0; i < variations; i++) {
            String suffix = "_" + i;
            ResourceLocation sideTex = sideTexFactory.apply(i);
            ResourceLocation bottomTex = bottomTexFactory.apply(i);
            ResourceLocation topTex = topTexFactory.apply(i);
            stairs[i] = models().stairs(blockName + suffix, sideTex, bottomTex, topTex);
            stairsInner[i] = models().stairsInner(blockName + "_inner" + suffix, sideTex, bottomTex, topTex);
            stairsOuter[i] = models().stairsOuter(blockName + "_outer" + suffix, sideTex, bottomTex, topTex);
        }

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    int yRot = (int) facing.getClockWise().toYRot();
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) yRot +=270;
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) yRot +=90;
                    yRot %=360;
                    boolean uvlock = yRot !=0 || half == Half.TOP;

                    ConfiguredModel.Builder builder = ConfiguredModel.builder();
                    for (int i =0; i < variations; i++) {
                        if (i >0) builder = builder.nextModel();
                        builder = builder .modelFile(shape == StairsShape.STRAIGHT ? stairs[i] : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner[i] : stairsOuter[i])
                                .rotationX(half == Half.BOTTOM ?0 :180)
                                .rotationY(yRot)
                                .uvLock(uvlock);
                    }

                    return builder.build();
                }, StairBlock.WATERLOGGED);
    }

    public ModelBuilder cubeBottomTopMirrored(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return this.cubeMirrored(name, bottom, top, side, side, side, side);
    }

    public ModelBuilder cubeMirrored(String name, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return this.models().withExistingParent(name, "cube_mirrored")
                .texture("down", down)
                .texture("up", up)
                .texture("north", north)
                .texture("south", south)
                .texture("east", east)
                .texture("west", west);
    }

    public ModelBuilder cross(Block block) {
        return this.models().cross(name(block), this.modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(block)));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private String name(BlockDefinition block) {
        return key(block.block()).getPath();
    }
}

