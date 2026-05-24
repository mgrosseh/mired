package com.mirandnyan.mired;

import com.mirandnyan.mired.ponder.MiredPonderPlugin;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

public class MiredDatagen {

    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(Mired.MOD_ID))
            addExtraRegistrateData();
    }

    private static void addExtraRegistrateData() {
        Mired.getRegistrate().addDataGenerator(ProviderType.LANG, provider -> {
            // Register this since FMLClientSetupEvent does not run during datagen
            PonderIndex.addPlugin(new MiredPonderPlugin());

            PonderIndex.getLangAccess().provideLang(Mired.MOD_ID, provider::add);
        });
    }
}
