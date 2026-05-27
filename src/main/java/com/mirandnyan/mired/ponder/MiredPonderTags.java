package com.mirandnyan.mired.ponder;

import com.mirandnyan.mired.MiredBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class MiredPonderTags {

    public static void register(final PonderTagRegistrationHelper<ResourceLocation> helper) {
        final PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(RegisteredObjectsHelper::getKeyOrThrow);

        // load order issues
//        itemHelper.addToTag(AllCreatePonderTags.REDSTONE)
//                .add(MiredBlocks.ANALOG_GATE_BLOCK)
//                .add(MiredBlocks.ANALOG_SR_LATCH_BLOCK)
//                .add(MiredBlocks.MEASURING_REDSTONE_LINK);
    }
}
