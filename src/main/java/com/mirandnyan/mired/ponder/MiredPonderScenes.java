package com.mirandnyan.mired.ponder;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.ponder.scenes.AnalogGateScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MiredPonderScenes {

    public static void register(final PonderSceneRegistrationHelper<ResourceLocation> registry) {
        final PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> helper =
                registry.withKeyFunction(DeferredHolder::getId);

        helper.forComponents(MiredBlocks.ANALOG_GATE_BLOCK)
                .addStoryBoard("analog_gate", AnalogGateScenes.GreateThanOrEqual::run);
    }
}
