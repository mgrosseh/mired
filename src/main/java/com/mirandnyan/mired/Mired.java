package com.mirandnyan.mired;

import com.mirandnyan.mired.registrate.MiredRegistrate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.simulated_team.simulated.registrate.SimulatedRegistrate;
import dev.simulated_team.simulated.util.SimColors;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Mired.MOD_ID)
public class Mired {
    public static final String MOD_ID = "mired";
    public static final String MOD_NAME = "Mired";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static final NonNullSupplier<MiredRegistrate> REGISTRATE = NonNullSupplier.lazy(() ->
            (MiredRegistrate) new MiredRegistrate(path("mired"), MOD_ID).defaultCreativeTab((ResourceKey)null));

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Mired(IEventBus modEventBus, ModContainer modContainer) {
        // setTooltips()

        //AllCreativeModeTabs.register(modEventBus);
        AllTags.register();
        AllBlocks.register();
        AllItems.register();
        AllBlockEntityTypes.register();
    }

    public static void setTooltips() {
        getRegistrate().setTooltipModifierFactory(item -> {
            final Rarity rarity = item.getDefaultInstance().getRarity();
            FontHelper.Palette color = FontHelper.Palette.STANDARD_CREATE;
            if (rarity == Rarity.EPIC)
                color = new FontHelper.Palette(TooltipHelper.styleFromColor(SimColors.EPIC_OURPLE), TooltipHelper.styleFromColor(rarity.color()));

            return new ItemDescription
                    .Modifier(item, color)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }


    public static MiredRegistrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation path(final String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}
