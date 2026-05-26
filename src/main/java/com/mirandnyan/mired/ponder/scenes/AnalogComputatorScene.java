package com.mirandnyan.mired.ponder.scenes;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.IntConsumer;

public class AnalogComputatorScene extends Scene {
    public static AnalogComputatorScene instance = new AnalogComputatorScene();
    public AnalogComputatorScene() {
        super("analog_computator", "Computing analog values with Analog Computators", 5);
    }

    @Override
    public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world, OverlayInstructions overlay,
                      SelectionUtil select, VectorUtil vector, EffectInstructions effects, PositionUtil grid) {
        scene.showBasePlate();


        final BlockPos backAnalogLeverPos = grid.at(2, 1, 0);
        final BlockPos backInputDust = grid.at(2, 1, 1);
        final BlockPos backInputNixiePos = grid.at(3, 1, 1);

        final BlockPos sideAnalogLeverPos = grid.at(0, 1, 2);
        final BlockPos sideInputDust = grid.at(1, 1, 2);
        final BlockPos sideInputNixiePos = grid.at(0, 1, 3);

        // make intellij shut up about some variable definitions
        //noinspection Duplicates
        final BlockPos outputDust = grid.at(2, 1, 3);
        final BlockPos outputNixiePos = grid.at(2, 1, 4);

        final BlockPos analogComputatorPos = grid.at(2, 1, 2);

        final Selection backInput = select.position(backAnalogLeverPos);
        final Selection backInputNixie = select.position(backInputNixiePos);
        final Selection sideInput = select.position(sideAnalogLeverPos);
        final Selection sideInputNixie = select.position(sideInputNixiePos);
        final Selection outputNixie = select.position(outputNixiePos);

        final Selection analogComputator = select.position(analogComputatorPos);

        final Selection initial = select.fromTo(backAnalogLeverPos, backInputDust).add(select.position(backInputNixiePos))
                .add(select.fromTo(sideAnalogLeverPos, sideInputDust).add(select.position(sideInputNixiePos)))
                .add(select.fromTo(outputDust, outputNixiePos));

        final Vec3 center = vector.centerOf(analogComputatorPos).add(0, -4 / 16.f, 0);
        final AABB leftOutline = getDiodeLeftOutline(analogComputatorPos);
        final AABB rightOutline = getDiodeRightOutline(analogComputatorPos);

        final Vec3 configPanel = vector.blockSurface(analogComputatorPos, Direction.DOWN).add(0, 3 / 16f, 0);

        IntConsumer setBackLever = (int power) -> {
            effects.indicateRedstone(backAnalogLeverPos);
            setAnalogLeverPower(world, backInput, power);
            setNixiePower(world, backInputNixie, power);
            setRedstoneWirePower(world, backInputDust, power);
            setAbstractRedstoneDiodeBackInput(world, analogComputatorPos, analogComputator, power);
        };
        IntConsumer setSideLever = (int power) -> {
            effects.indicateRedstone(sideAnalogLeverPos);
            setAnalogLeverPower(world, sideInput, power);
            setNixiePower(world, sideInputNixie, power);
            setRedstoneWirePower(world, backInputDust, power);
            setAbstractBinaryRedstoneDiodeRightInput(world, analogComputatorPos, analogComputator, power);
        };

        IntConsumer updateOutput = (int power) -> {
            setAbstractRedstoneDiodeOutput(world, analogComputatorPos, analogComputator, power);
            setRedstoneWirePower(world, outputDust, power);
            setNixiePower(world, outputNixie, power);
        };

        // DRAMATIC ENTRANCE OF ANALOG COMPUTATOR
        scene.addKeyframe();

        world.showSection(initial, Direction.UP);
        scene.idle(5);
        world.showSection(analogComputator, Direction.DOWN);
        scene.idle(20);

        overlay.showText(60)
                .placeNearTarget()
                .pointAt(center)
                .text("Analog Computators perform simple arithmetic operations based on analog input signals");
        scene.idle(70);

        // INPUTS EXPLAINED
        overlay.showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("By default the output signal is the result of adding both the back-input and side-input");
        scene.idle(70);

        setBackLever.accept(3);
        updateOutput.accept(3);

        scene.idle(30);

        overlay.chaseBoundingBoxOutline(PonderPalette.RED, leftOutline, leftOutline, 80);
        overlay.chaseBoundingBoxOutline(PonderPalette.RED, rightOutline, rightOutline, 80);

        overlay.showText(80)
                .placeNearTarget()
                .pointAt(center)
                .text("Both side inputs act as one input, which ever side has the highest value is \"the\" side-input");
        scene.idle(90);

        setSideLever.accept(4);
        updateOutput.accept(7);

        scene.idle(10);

        overlay.showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("The output updates instantly whenever any of the inputs change");
        scene.idle(66);

        // MODES EXPLAINED
        AnalogComputatorBlockEntity.ComputationMode add = AnalogComputatorBlockEntity.ComputationMode.ADDITION;
        AnalogComputatorBlockEntity.ComputationMode sub = AnalogComputatorBlockEntity.ComputationMode.SUBTRACTION;
        AnalogComputatorBlockEntity.ComputationMode mul = AnalogComputatorBlockEntity.ComputationMode.MULTIPLICATION;
        AnalogComputatorBlockEntity.ComputationMode div = AnalogComputatorBlockEntity.ComputationMode.DIVISION;

        overlay.showFilterSlotInput(configPanel, Direction.UP, 50);
        overlay.showText(50)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("The computation mode can be changed with the panel");

        scene.idle(60);

        // ADDITION
        overlay.showControls(configPanel, Pointing.DOWN, 40).showing(add.getIcon());
        overlay.showText(30)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("Addition");
        scene.idle(10);

        //noinspection ConstantValue
        world.modifyBlockEntity(analogComputatorPos, AnalogComputatorBlockEntity.class,
                be -> be.getBehaviour(ScrollOptionBehaviour.TYPE).setValue(add.ordinal()));


        setBackLever.accept(5);
        setSideLever.accept(4);
        updateOutput.accept(5 + 4);

        scene.idle(60);

        // SUBTRACTION
        overlay.showControls(configPanel, Pointing.DOWN, 40).showing(sub.getIcon());
        overlay.showText(30)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("Subtraction");
        scene.idle(10);

        world.modifyBlockEntity(analogComputatorPos, AnalogComputatorBlockEntity.class,
                be -> be.getBehaviour(ScrollOptionBehaviour.TYPE).setValue(sub.ordinal()));

        setBackLever.accept(5);
        setSideLever.accept(4);
        updateOutput.accept(5 - 4);

        scene.idle(60);

        // MULTIPLICATION
        overlay.showControls(configPanel, Pointing.DOWN, 40).showing(mul.getIcon());
        overlay.showText(30)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("Multiplication");
        scene.idle(10);

        world.modifyBlockEntity(analogComputatorPos, AnalogComputatorBlockEntity.class,
                be -> be.getBehaviour(ScrollOptionBehaviour.TYPE).setValue(mul.ordinal()));

        setBackLever.accept(3);
        setSideLever.accept(4);
        updateOutput.accept(3 * 4);

        scene.idle(60);

        // DIVISION
        overlay.showControls(configPanel, Pointing.DOWN, 40).showing(div.getIcon());
        overlay.showText(30)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(center)
                .text("Division");
        scene.idle(10);

        world.modifyBlockEntity(analogComputatorPos, AnalogComputatorBlockEntity.class,
                be -> be.getBehaviour(ScrollOptionBehaviour.TYPE).setValue(div.ordinal()));

        setBackLever.accept(12);
        setSideLever.accept(4);
        updateOutput.accept(12 / 4);

        scene.idle(70);

        overlay.showText(30)
                .placeNearTarget()
                .pointAt(center)
                .text("Division rounds down");

        setBackLever.accept(5);
        setSideLever.accept(3);
        updateOutput.accept(5 / 3);

        scene.idle(30);

    }
}
