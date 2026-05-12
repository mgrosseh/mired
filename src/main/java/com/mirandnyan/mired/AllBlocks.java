package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlock;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockStateGen;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

@SuppressWarnings("removal")
public class AllBlocks {
    private static final CreateRegistrate REGISTRATE = Mired.getRegistrate();


    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }


    public static final BlockEntry<AnalogSRLatchBlock> ANALOG_SR_LATCH =
            REGISTRATE.block("analog_sr_latch", AnalogSRLatchBlock::new)
                    .initialProperties(() -> Blocks.REPEATER)
                    .blockstate(AnalogSRLatchBlockStateGen.generate())
                    .tag(com.simibubi.create.AllTags.AllBlockTags.SAFE_NBT.tag, AllTags.Blocks.DIODE)
//                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
//                            .pattern(" Q ")
//                            .pattern("RBT")
//                            .pattern("SSS")
//                            .define('T', Blocks.REDSTONE_TORCH)
//                            .define('B', com.simibubi.create.AllItems.BRASS_SHEET.get())
//                            .define('R', SimTags.Items.REDSTONE_DUST)
//                            .define('Q', AllItems.POLISHED_ROSE_QUARTZ)
//                            .define('S', SimTags.Items.STONE)
//                            .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(SimTags.Items.REDSTONE_DUST))
//                            .save(p))
                    .item()
                    .transform(customItemModel())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .register();

    // Load this class
    public static void register() {}
}
