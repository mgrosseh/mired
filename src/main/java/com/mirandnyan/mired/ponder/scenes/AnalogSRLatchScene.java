package com.mirandnyan.mired.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.*;

public class AnalogSRLatchScene extends Scene {
    public static AnalogSRLatchScene instance = new AnalogSRLatchScene();
    public AnalogSRLatchScene() {
        super("analog_sr_latch", "Storing analog signals with Analog SR Latches", 5);
    }

    @Override
    public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world, OverlayInstructions overlay, SelectionUtil select, VectorUtil vectorUtil, EffectInstructions effects, PositionUtil grid) {
        scene.showBasePlate();



    }
}
