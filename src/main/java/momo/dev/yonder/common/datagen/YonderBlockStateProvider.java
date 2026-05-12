package momo.dev.yonder.common.datagen;

import com.farcr.nomansland.NoMansLand;
import com.farcr.nomansland.common.definitions.BlockDefinition;
import momo.dev.yonder.Yonder;
import momo.dev.yonder.common.registry.YonderBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
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

        // Dead Leaves
        simpleBlockWithItem(DEAD_LEAVES.get(), models().leaves(name(DEAD_LEAVES), modLoc("block/dead_leaves")));
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

