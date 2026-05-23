package com.mirandnyan.mired;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MiredItems {
    private static final CreateRegistrate REGISTRATE = Mired.getRegistrate();

    public static final ItemEntry<Item> DIODE_BASE = REGISTRATE.item("diode_base", Item::new)
            .model((ctx, prov) ->
                    prov.getExistingFile(ResourceLocation.fromNamespaceAndPath(Mired.MOD_ID, "item/diode_base")))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("RBR")
                    .pattern("SSS")
                    .define('R', Items.REDSTONE)
                    .define('B', AllItems.BRASS_SHEET)
                    .define('S', MiredTags.Items.STONES)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllItems.BRASS_SHEET))
                    .save(p, Mired.asResource("diode_base"))
            )
            .register();

    public static final ItemEntry<Item> COMPUTATION_CIRCUIT = REGISTRATE.item("computation_circuit", Item::new)
            .model((ctx, prov) -> prov.generated(ctx::getEntry))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern(" B ")
                    .pattern("RCR")
                    .pattern(" B ")
                    .define('B', AllItems.BRASS_SHEET)
                    .define('R', Items.REDSTONE)
                    .define('C', Items.COMPARATOR)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllItems.BRASS_SHEET))
                    .save(p, Mired.asResource("computation_circuit"))
            )
            .register();



    public static void register() {
        // load class
    }
}
