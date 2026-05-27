package com.mirandnyan.mired.ponder.scenes;

import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlock;
import com.mirandnyan.mired.content.blocks.helpers.binary_redstone_diode.AbstractBinaryRedstoneDiodeBlock;
import com.mirandnyan.mired.content.blocks.helpers.binary_redstone_diode.AbstractBinaryRedstoneDiodeBlockEntity;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlockEntity;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.phys.AABB;

public abstract class Scene {
    private final String title;
    private final String sceneId;
    private final int basePlateSize;
    private final int basePlateXOffset;
    private final int basePlateZOffset;

    public Scene(String sceneId, String title, int basePlateSize) {
        this(sceneId, title, basePlateSize, 0, 0);

    }
    public Scene(String sceneId, String title,
                 int basePlateSize, int basePlateXOffset, int basePlateZOffset) {
        this.sceneId = sceneId;
        this.title = title;
        this.basePlateSize = basePlateSize;
        this.basePlateXOffset = basePlateXOffset;
        this.basePlateZOffset = basePlateZOffset;
    }

    public void run(final SceneBuilder builder, final SceneBuildingUtil util) {
        final CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        final CreateSceneBuilder.WorldInstructions world = scene.world();
        final OverlayInstructions overlays = scene.overlay();
        final SelectionUtil select = util.select();
        final VectorUtil vector = util.vector();
        final EffectInstructions effects = scene.effects();
        final PositionUtil grid = util.grid();

        scene.title(sceneId, title);
        scene.configureBasePlate(basePlateXOffset, basePlateZOffset, basePlateSize);

        scene(scene, world, overlays, select, vector, effects, grid);
    }

    public abstract void scene(final CreateSceneBuilder scene, final CreateSceneBuilder.WorldInstructions world,
                               final OverlayInstructions overlay, final SelectionUtil select, final VectorUtil vector,
                               final EffectInstructions effects, final PositionUtil grid);



    protected static void stepAnalogLever(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world,
                                          EffectInstructions effects, PositionUtil grid,
                                          BlockPos analogLeverPos, Selection analogLever, BlockPos inputDust,
                                          Selection inputNixie, BlockPos diodePos, Selection diode,
                                          int from, int to, int step) {
        for (int i = from; i <= to; i += step) {
            scene.idle(15);
            effects.indicateRedstone(analogLeverPos);
            setAnalogLeverPower(world, analogLever, i);
            setNixiePower(world, inputNixie, i);
            setRedstoneWirePower(world, inputDust, i);
            setAbstractRedstoneDiodeBackInput(world, diodePos, diode, i);
        }
    }


    protected static AABB getDiodeBaseOutline(BlockPos pos) {
        return new AABB(pos).inflate(-0.05f, -0.45f, -0.08f).move(0, -.45f, 0);
    }

    protected static AABB getDiodeBackOutline(BlockPos pos) {
        return new AABB(pos).inflate(-.05f, -.45f, -.48f).move(0, -.45, -.575);
    }
    protected static AABB getDiodeFrontOutline(BlockPos pos) {
        return new AABB(pos).inflate(-.05f, -.45f, -.48f).move(0, -.45, .575);
    }
    protected static AABB getDiodeLeftOutline(BlockPos pos) {
        return new AABB(pos).inflate(-.48f, -.45f, -.05f).move(.575, -.45, 0);
    }
    protected static AABB getDiodeRightOutline(BlockPos pos) {
        return new AABB(pos).inflate(-.48f, -.45f, -.05f).move(-.575, -.45, 0);
    }

    protected static void setAbstractRedstoneDiodeBackInput(CreateSceneBuilder.WorldInstructions world, BlockPos pos, Selection sel, int power) {
        world.modifyBlock(pos, s -> s.setValue(AbstractRedstoneDiodeBlock.POWERED, power > 0), false);
        world.modifyBlockEntityNBT(sel, AbstractBinaryRedstoneDiodeBlockEntity.class, tag -> tag.putInt("BackInputSignal", power));
    }
    protected static void setAbstractRedstoneDiodeOutput(CreateSceneBuilder.WorldInstructions world, BlockPos pos, Selection sel, int power) {
        world.modifyBlock(pos, s -> s.setValue(AbstractRedstoneDiodeBlock.POWERING, power > 0), false);
        world.modifyBlockEntityNBT(sel, AbstractBinaryRedstoneDiodeBlockEntity.class, tag -> tag.putInt("OutputSignal", power));
    }


    protected static void setAbstractBinaryRedstoneDiodeLeftInput(CreateSceneBuilder.WorldInstructions world, BlockPos pos, Selection sel, int power) {
        world.modifyBlock(pos, s -> s.setValue(AbstractBinaryRedstoneDiodeBlock.SIDE_POWERED, power > 0), false);
        world.modifyBlockEntityNBT(sel, AbstractBinaryRedstoneDiodeBlockEntity.class, tag -> tag.putInt("LeftInputSignal", power));
    }
    protected static void setAbstractBinaryRedstoneDiodeRightInput(CreateSceneBuilder.WorldInstructions world, BlockPos pos, Selection sel, int power) {
        world.modifyBlock(pos, s -> s.setValue(AbstractBinaryRedstoneDiodeBlock.SIDE_POWERED, power > 0), false);
        world.modifyBlockEntityNBT(sel, AbstractBinaryRedstoneDiodeBlockEntity.class, tag -> tag.putInt("RightInputSignal", power));
    }

    protected static void setAnalogLeverPower(CreateSceneBuilder.WorldInstructions world, Selection sel, int power) {
        world.modifyBlockEntityNBT(sel, AnalogLeverBlockEntity.class, tag -> tag.putInt("State", power));
    }
    protected static void setNixiePower(CreateSceneBuilder.WorldInstructions world, Selection sel, int power) {
        world.modifyBlockEntityNBT(sel, NixieTubeBlockEntity.class, tag -> tag.putInt("RedstoneStrength", power));
    }
    protected static void setRedstoneWirePower(CreateSceneBuilder.WorldInstructions world, BlockPos pos, int power) {
        world.modifyBlock(pos, s -> s.setValue(RedStoneWireBlock.POWER, power), false);
    }
}
