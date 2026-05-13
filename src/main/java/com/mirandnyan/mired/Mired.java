package com.mirandnyan.mired;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Mired.MOD_ID)
public class Mired {
    public static final String MOD_ID = "mired";
    public static final String MOD_NAME = "Mired";

    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Mired(IEventBus modEventBus, ModContainer modContainer) {
        MiredBlocks.register(modEventBus);
        MiredItems.register(modEventBus);
        MiredBlockEntityTypes.register(modEventBus);
        MiredCreativeModeTabs.register(modEventBus);
    }

    public static ResourceLocation path(final String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}
