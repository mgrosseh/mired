package com.mirandnyan.mired.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.*;

public class AnalogComputatorScene extends Scene {
    public static AnalogComputatorScene instance = new AnalogComputatorScene();
    public AnalogComputatorScene() {
        super("analog_computator", "Computing analog values with Analog Computators", 5);
    }

    @Override
    public void scene(CreateSceneBuilder scene, CreateSceneBuilder.WorldInstructions world, OverlayInstructions overlay, SelectionUtil select, VectorUtil vector, EffectInstructions effects, PositionUtil grid) {

    }
}
