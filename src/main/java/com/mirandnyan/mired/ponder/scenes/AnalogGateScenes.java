package com.mirandnyan.mired.ponder.scenes;

import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AnalogGateScenes {
    public static Scene GreateThanOrEqual = new Scene("analog_gate", "Using Analog Gates", 5) {
        @Override
        public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world,
                          OverlayInstructions overlay, SelectionUtil select, VectorUtil vector,
                          EffectInstructions effects, PositionUtil grid) {
            scene.showBasePlate();

            // define initial state
            final BlockPos analogLeverPos = grid.at(2, 1, 0);
            final BlockPos inputDust = grid.at(2, 1, 1);
            final BlockPos inputNixiePos = grid.at(1, 1, 1);
            final BlockPos analogGatePos = grid.at(2, 1, 2);
            final BlockPos outputDust = grid.at(2, 1, 3);
            final BlockPos outputNixiePos = grid.at(2, 1, 4);

            final Selection analogLever = select.position(analogLeverPos);
            final Selection inputNixie = select.position(inputNixiePos);
            final Selection outputNixie = select.position(outputNixiePos);
            final Selection analogGate = select.position(analogGatePos);
            final Selection input = select.fromTo(analogLeverPos, inputDust).add(select.position(inputNixiePos));
            final Selection output = select.fromTo(outputDust, outputNixiePos);

            // TODO final
            final Vec3 analogGateCenter = vector.centerOf(analogGatePos).add(0, -4 / 16.f, 0);
            final AABB baseOutline = getDiodeBaseOutline(analogGatePos);
            final Vec3 strengthConfigPanel = vector.blockSurface(analogGatePos, Direction.DOWN).add(0, 3 / 16f, 0);
            final Vec3 textAtBasePlate = analogGateCenter.add(-0.35, -0.2, 0.35);

            // DRAMATIC ENTRANCE OF ANALOG GATE
            scene.addKeyframe();

            world.showSection(input.add(output), Direction.UP);
            scene.idle(5);
            world.showSection(analogGate, Direction.DOWN);
            scene.idle(20);

            overlay.showText(100)
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("Analog Gates only allow analog signals of certain strengths to be passed along");
            scene.idle(120);

            // SWITCHING MODES
            overlay.chaseBoundingBoxOutline(PonderPalette.WHITE, baseOutline, baseOutline, 120);
            overlay.showControls(textAtBasePlate, Pointing.DOWN, 120).rightClick();

            overlay.showText(120)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(textAtBasePlate)
                    .text("By default, Analog Gates are in \"Greater than or Equal\" mode, " +
                                    "this can be changed by right-clicking the block base");
            scene.idle(140);

            // CHANGING CONFIGURED STRENGTH
            overlay.showFilterSlotInput(strengthConfigPanel, Direction.UP, 80);
            overlay.showControls(strengthConfigPanel, Pointing.DOWN, 70).rightClick();
            scene.idle(10);
            overlay.showText(60)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(strengthConfigPanel)
                    .text("Using the value panel, the selected signal strength can be selected"); // TODO
            world.modifyBlockEntity(analogGatePos, AnalogGateBlockEntity.class,
                    be -> be.getBehaviour(ScrollOptionBehaviour.TYPE).setValue(10));
            scene.idle(80);
            overlay.showText(30)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(strengthConfigPanel)
                    .text("Now set to 10");
            scene.idle(50);

            // DEMO 1
            overlay.showText(80)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("When the input signal strength is less than the configured value, the output will be 0");

            for (int i = 0; i <= 8; i += 2) {
                scene.idle(15);
                effects.indicateRedstone(grid.at(2, 1, 0));
                setAnalogLeverPower(world, analogLever, i);
                setNixiePower(world, inputNixie, i);
                setRedstoneWirePower(world, inputDust, i);
                setAbstractRedstoneDiodeBackInput(world, analogGatePos, analogGate, i);
            }

            scene.idle(30);

            overlay.showText(30)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("Once the value is reached, ");

            scene.idle(15);
            effects.indicateRedstone(grid.at(2, 1, 0));
            setAnalogLeverPower(world, analogLever, 9);
            setNixiePower(world, inputNixie, 9);
            setRedstoneWirePower(world, inputDust, 9);
            setAbstractRedstoneDiodeBackInput(world, analogGatePos, analogGate, 9);

            scene.idle(15);
            effects.indicateRedstone(grid.at(2, 1, 0));
            setAnalogLeverPower(world, analogLever, 10);
            setNixiePower(world, inputNixie, 10);
            setRedstoneWirePower(world, inputDust, 10);
            setAbstractRedstoneDiodeBackInput(world, analogGatePos, analogGate, 10);

            overlay.showText(60)
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("Once the value is reached, the output signal strength will equal the input");

            setAbstractRedstoneDiodeOutput(world, analogGatePos, analogGate, 10);
            setAbstractRedstoneDiodeBackInput(world, analogGatePos, analogGate, 10);
            setRedstoneWirePower(world, outputDust, 10);
            setNixiePower(world, outputNixie, 10);

            scene.idle(80);

            // SWITCHING LESS THAN OR EQUAL MODE
            overlay.chaseBoundingBoxOutline(PonderPalette.WHITE, baseOutline, baseOutline, 120);
            overlay.showControls(textAtBasePlate, Pointing.DOWN, 120).rightClick();

            world.modifyBlockEntityNBT(analogGate, AnalogGateBlockEntity.class, tag
                    -> tag.putString("AnalogGateMode", AnalogGateBlockEntity.AnalogGateMode.LESS_EQUAL.toString()));

            overlay.showText(120)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(textAtBasePlate)
                    .text("Now, right-clicking the base cycles it's mode. " +
                            "Since the input is equal to the selected value, nothing changes");
            scene.idle(140);

            // DEMO 2
            overlay.showText(17)
                    .attachKeyFrame()
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("But increasing the value now, ");

            scene.idle(15);
            effects.indicateRedstone(grid.at(2, 1, 0));
            setAnalogLeverPower(world, analogLever, 11);
            setNixiePower(world, inputNixie, 11);
            setRedstoneWirePower(world, inputDust, 11);

            scene.idle(2);

            overlay.showText(80)
                    .placeNearTarget()
                    .pointAt(analogGateCenter)
                    .text("But increasing the value now, turns the output off again, " +
                                    "since it is greater, when in less than or equal mode");

            setAbstractRedstoneDiodeBackInput(world, analogGatePos, analogGate, 0);
            setAbstractRedstoneDiodeOutput(world, analogGatePos, analogGate, 0);
            setRedstoneWirePower(world, outputDust, 0);
            setNixiePower(world, outputNixie, 0);

            scene.idle(80);
        }
    };
}
