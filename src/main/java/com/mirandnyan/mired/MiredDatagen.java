package com.mirandnyan.mired;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mirandnyan.mired.ponder.MiredPonderPlugin;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
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

            MiredTooltips.register(provider::add);

            PonderIndex.getLangAccess().provideLang(Mired.MOD_ID, provider::add);
        });
    }
}
