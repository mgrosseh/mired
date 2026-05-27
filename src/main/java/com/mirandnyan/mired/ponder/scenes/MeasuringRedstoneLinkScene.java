package com.mirandnyan.mired.ponder.scenes;

import com.mirandnyan.mired.content.blocks.measuring_redstone_link.MeasuringRedstoneLinkBlock;
import com.simibubi.create.content.redstone.link.RedstoneLinkBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec3;

public class MeasuringRedstoneLinkScene extends Scene {
    public static MeasuringRedstoneLinkScene instance = new MeasuringRedstoneLinkScene();
    public MeasuringRedstoneLinkScene() {
        super("measuring_redstone_link", "Using Measuring Redstone Links", 5);
    }

    @Override
    public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world, OverlayInstructions overlay,
                      SelectionUtil select, VectorUtil vector, EffectInstructions effects, PositionUtil grid) {
        scene.showBasePlate();

        final BlockPos transmitLinkPos = grid.at(1, 2, 1);
        final BlockPos bookshelfPos = grid.at(1, 1, 1);

        final BlockPos comparatorPos = grid.at(2, 1, 1);
        final BlockPos regularSendPos = grid.at(3, 1, 1);
        final BlockPos leverPos = grid.at(4, 1, 1);

        final BlockPos measuringLinkPos = grid.at(3, 1, 4);
        final BlockPos measuringNixiePos = grid.at(4, 1, 4);
        final BlockPos regularLinkPos = grid.at(2, 1, 4);
        final BlockPos regularNixiePos = grid.at(1, 1, 4);


        //noinspection DuplicatedCode
        final Selection transmitLink = select.position(transmitLinkPos);
        final Selection bookshelf = select.position(bookshelfPos);
        final Selection regularSend = select.position(regularSendPos);
        final Selection comparator = select.position(comparatorPos);
        final Selection lever = select.position(leverPos);

        final Selection measuringLink = select.position(measuringLinkPos);
        final Selection measuringNixie = select.position(measuringNixiePos);
        final Selection regularLink = select.position(regularLinkPos);
        final Selection regularNixie = select.position(regularNixiePos);

        Selection initial = bookshelf.add(measuringNixie);

        final Vec3 centerSend = vector.centerOf(transmitLinkPos).add(0, -4 / 16.f, 0);
        final Vec3 centerMeasure = vector.centerOf(measuringLinkPos).add(0, -4 / 16.f, 0);

        // TODO: show controls on bookshelf for inserting book

        // DRAMATIC ENTRANCE OF MEASURING REDSTONE LINK
        scene.addKeyframe();

        world.showSection(initial, Direction.UP);
        scene.idle(5);
        // TODO fix schematic
        world.modifyBlock(regularSendPos, state -> state.setValue(RedstoneLinkBlock.RECEIVER, false), false);
        world.showSection(transmitLink.add(measuringLink), Direction.DOWN);
        scene.idle(20);

        overlay.showText(50)
                .placeNearTarget()
                .pointAt(centerSend)
                .pointAt(centerMeasure)
                .text("Measuring Redstone Links are related to Redstone Links");
        scene.idle(60);

        // Transmit mode
        overlay.showText(80)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(centerSend)
                .text("In transmit mode Measuring Redstone Links work like a comparator that is powering a Redstone Link");
        scene.idle(90);

        world.showSection(comparator, Direction.DOWN);
        scene.idle(1);
        world.showSection(regularSend, Direction.DOWN);
        scene.idle(40);
        world.hideSection(regularSend.add(comparator), Direction.UP);
        scene.idle(30);

        var bookSlots = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES;
        final Vec3 bookshelfMid = vector.blockSurface(bookshelfPos, Direction.DOWN);

        overlay.showControls(bookshelfMid, Pointing.UP, 20).withItem(Items.BOOK.getDefaultInstance());
        world.modifyBlock(bookshelfPos, state -> state.setValue(bookSlots.getFirst(), true), false);
        world.modifyBlock(transmitLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        world.modifyBlock(measuringLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        setNixiePower(world, measuringNixie, 1);

        scene.idle(10);

        overlay.showText(100)
                .placeNearTarget()
                .pointAt(centerSend)
                .text("When attached to a block that can be read from a comparator, "
                        + "it emits the analog signal a comparator would on the configured Redstone Link Frequency");
        scene.idle(110);

        overlay.showControls(bookshelfMid, Pointing.UP, 20).withItem(Items.BOOK.getDefaultInstance());
        world.modifyBlock(bookshelfPos, state -> state.setValue(bookSlots.get(3), true), false);
        world.modifyBlock(transmitLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        world.modifyBlock(measuringLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        setNixiePower(world, measuringNixie, 4);


        // RECEIVE MODE
        overlay.showText(80)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(centerMeasure)
                .text("Measuring Redstone Links in Receive mode behave exactly like regular Redstone Links");
        scene.idle(90);

        world.modifyBlock(regularLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        setNixiePower(world, regularNixie, 4);
        world.showSection(regularNixie.add(regularLink), Direction.DOWN);
        scene.idle(50);


        scene.addKeyframe();

        world.hideSection(bookshelf.add(transmitLink), Direction.UP);
        world.modifyBlock(regularLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, false), false);
        setNixiePower(world, regularNixie, 0);
        world.modifyBlock(measuringLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, false), false);
        setNixiePower(world, measuringNixie, 0);
        scene.idle(30);

        overlay.showText(80)
                .placeNearTarget()
                .pointAt(centerMeasure)
                .text("That means Measuring Redstone Links also receive signals send by regular Redstone Links");
        scene.idle(90);

        world.setBlock(leverPos, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        world.showSection(regularSend.add(lever), Direction.DOWN);
        scene.idle(10);

        effects.indicateRedstone(leverPos);
        world.toggleRedstonePower(lever);
        world.modifyBlock(regularSendPos, state -> state.setValue(RedstoneLinkBlock.POWERED, true), false);
        scene.idle(2);

        world.modifyBlock(regularLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        setNixiePower(world, regularNixie, 15);
        world.modifyBlock(measuringLinkPos, state -> state.setValue(MeasuringRedstoneLinkBlock.POWERED, true), false);
        setNixiePower(world, measuringNixie, 15);
    }
}
