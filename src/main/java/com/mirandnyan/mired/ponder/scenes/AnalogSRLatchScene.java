package com.mirandnyan.mired.ponder.scenes;

import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockEntity;
import com.mirandnyan.mired.content.blocks.helpers.binary_redstone_diode.AbstractBinaryRedstoneDiodeBlockEntity;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AnalogSRLatchScene extends Scene {
    public static AnalogSRLatchScene instance = new AnalogSRLatchScene();
    public AnalogSRLatchScene() {
        super("analog_sr_latch", "Storing analog signals with Analog SR Latches", 5);
    }

    @Override
    public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world, OverlayInstructions overlay, SelectionUtil select, VectorUtil vector, EffectInstructions effects, PositionUtil grid) {
        scene.showBasePlate();

        final BlockPos analogLeverPos = grid.at(2, 1, 0);
        final BlockPos backInputDust = grid.at(2, 1, 1);
        final BlockPos backInputNixiePos = grid.at(3, 1, 1);

        final BlockPos sideInputPos = grid.at(1, 1, 2);

        final BlockPos outputDust = grid.at(2, 1, 3);
        final BlockPos outputNixiePos = grid.at(2, 1, 4);

        final BlockPos analogSRLatchPos = grid.at(2, 1, 2);


        final Selection analogLever = select.position(analogLeverPos);
        final Selection inputNixie = select.position(backInputNixiePos);
        final Selection sideInput = select.position(sideInputPos);
        final Selection outputNixie = select.position(outputNixiePos);
        final Selection analogSRLatch = select.position(analogSRLatchPos);

        final Selection initial = select.fromTo(analogLeverPos, backInputDust).add(select.position(backInputNixiePos))
                .add(select.fromTo(outputDust, outputNixiePos)).add(sideInput);


        final Vec3 center = vector.centerOf(analogSRLatchPos).add(0, -4 / 16.f, 0);
        final AABB baseOutline = getDiodeBaseOutline(analogSRLatchPos);
        final AABB backOutline = getDiodeBackOutline(analogSRLatchPos);
        AABB sideOutline = getDiodeRightOutline(analogSRLatchPos); // TODO final


        // DRAMATIC ENTRANCE OF ANALOG SR LATCH
        scene.addKeyframe();

        world.showSection(initial, Direction.UP);
        scene.idle(5);
        world.showSection(analogSRLatch, Direction.DOWN);
        scene.idle(20);

        overlay.showText(40)
                .placeNearTarget()
                .pointAt(center)
                .text("Analog SR Latches are redstone controllable Analog Levers");
        scene.idle(50);

        // INPUTS EXPLAINED
        stepAnalogLever(scene, world, effects, grid,
                analogLeverPos, analogLever, backInputDust, inputNixie, analogSRLatchPos, analogSRLatch,
                1, 3, 1);

        scene.idle(10);

        overlay.showText(80)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("Their state is only updated when they receive a signal from one of the side inputs");

        scene.idle(60);

        effects.indicateRedstone(sideInputPos);
        world.toggleRedstonePower(sideInput);
        setAbstractBinaryRedstoneDiodeRightInput(world, analogSRLatchPos, analogSRLatch, 15);

        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 3);

        scene.idle(16);
        world.toggleRedstonePower(sideInput);
        setAbstractBinaryRedstoneDiodeRightInput(world, analogSRLatchPos, analogSRLatch, 0);

        scene.idle(30);

        // DIFFERENT MODES
        // ON RISING EDGE MODE
        overlay.showText(150)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("By default, Analog SR Latches are in \"On Rising Edge\" update mode, " +
                        "where they will only trigger one time -- instantly -- when the side input turned on.");
        scene.idle(160);

        scene.addKeyframe();

        world.destroyBlock(sideInputPos);
        world.setBlock(sideInputPos, Blocks.LEVER.defaultBlockState()
                        .setValue(LeverBlock.FACE, AttachFace.FLOOR), true);

        stepAnalogLever(scene, world, effects, grid,
                analogLeverPos, analogLever, backInputDust, inputNixie, analogSRLatchPos, analogSRLatch,
                3, 5, 1);

        scene.idle(10);

        effects.indicateRedstone(sideInputPos);
        world.toggleRedstonePower(sideInput);

        setAbstractBinaryRedstoneDiodeRightInput(world, analogSRLatchPos, analogSRLatch, 15);

        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 5);

        scene.idle(10);

        overlay.showText(80)
                .placeNearTarget()
                .pointAt(center)
                .text("So if the back input value changes after the initial powering, the value no longer updates");
        scene.idle(90);

        stepAnalogLever(scene, world, effects, grid,
                analogLeverPos, analogLever, backInputDust, inputNixie, analogSRLatchPos, analogSRLatch,
                6, 7, 1);

        scene.idle(20);

        // CONTINUOUS MODE
        scene.addKeyframe();

        overlay.chaseBoundingBoxOutline(PonderPalette.WHITE, baseOutline, baseOutline, 120);
        overlay.showControls(center, Pointing.DOWN, 120).withItem(AllItems.WRENCH.asStack());

        world.modifyBlockEntityNBT(analogSRLatch, AnalogSRLatchBlockEntity.class, tag -> tag.putBoolean("RisingEdgeOnlyMode", false));

        overlay.showText(120)
                .placeNearTarget()
                .pointAt(center)
                .text("This can be changed by right-clicking the Analog SR Latch with a Wrench,"
                        + " switching it to \"Continuous\" update mode");
        scene.idle(140);

        scene.addKeyframe();

        overlay.showText(100)
                .placeNearTarget()
                .pointAt(center)
                .text("Now when the side input remains turned on, change of the back input, is reflected in the output");
        scene.idle(96);

        effects.indicateRedstone(sideInputPos);
        world.toggleRedstonePower(sideInput);

        setAbstractBinaryRedstoneDiodeRightInput(world, analogSRLatchPos, analogSRLatch, 15);

        scene.idle(4);

        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 7);

        scene.idle(10);

        overlay.showText(100)
                .placeNearTarget()
                .pointAt(center)
                .text("Unlike \"On Rising Edge\" mode \"Continuous\" mode has a 2 tick (1 redstone tick)"
                        + " delay, which can be useful for circuits chaining or back-feeding Analog SR Latches");
        scene.idle(100);

        stepAnalogLever(scene, world, effects, grid,
                analogLeverPos, analogLever, backInputDust, inputNixie, analogSRLatchPos, analogSRLatch,
                8, 8, 1);

        scene.idle(4);

        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 8);

        stepAnalogLever(scene, world, effects, grid,
                analogLeverPos, analogLever, backInputDust, inputNixie, analogSRLatchPos, analogSRLatch,
                9, 9, 1);

        scene.idle(4);

        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 9);

        // MANUALLY SETTING VALUE
        scene.idle(10);
        world.destroyBlock(backInputNixiePos);
        world.destroyBlock(backInputDust);
        world.destroyBlock(analogLeverPos);
        world.destroyBlock(sideInputPos);

        scene.idle(10);

        scene.addKeyframe();

        overlay.showText(140)
                .placeNearTarget()
                .pointAt(center)
                .text("While not being continuously updated, the state of Analog SR Latches can be modified"
                        + " by right-clicking to increase the value and sneak-right-clicking to decrease the value");
        scene.idle(160);

        overlay.showControls(center, Pointing.DOWN, 30).rightClick();
        for (int i = 10; i <= 15; i++) {
            scene.idle(2);
            setAbstractRedstoneDiodeOutput(world, analogSRLatchPos, analogSRLatch, i);
        }
        scene.idle(15);
        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 15);

        scene.idle(30);


        overlay.showControls(center, Pointing.DOWN, 60).rightClick().whileSneaking();
        for (int i = 14; i >= 0; i--) {
            scene.idle(2);
            setAbstractRedstoneDiodeOutput(world, analogSRLatchPos, analogSRLatch, i);
        }
        scene.idle(15);
        setOutputs(world, analogSRLatchPos, analogSRLatch, outputDust, outputNixie, 0);
    }

    protected void setOutputs(CreateSceneBuilder.WorldInstructions world,
                              BlockPos pos, Selection sel, BlockPos dust, Selection nixie, int power) {
        setAbstractRedstoneDiodeOutput(world, pos, sel, power);
        setRedstoneWirePower(world, dust, power);
        setNixiePower(world, nixie, power);
    }
}
