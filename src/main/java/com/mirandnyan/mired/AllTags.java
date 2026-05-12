package com.mirandnyan.mired;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AllTags {

    public static void addGenerators() {
        Blocks.addGenerators();
        Items.addGenerators();
    }

    public static class Blocks {
        //public static final TagKey<Block> EXAMPLE = create("example");
        public static final TagKey<Block> DIODE = create("sable", "diode");

        private static TagKey<Block> create(final String path) {
            return TagKey.create(Registries.BLOCK, Mired.path(path));
        }
        private static TagKey<Block> create(final String namespace, final String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
        protected static void addGenerators() {
            Mired.getRegistrate().addDataGenerator(ProviderType.BLOCK_TAGS, Blocks::genBlockTags);
        }

        private static void genBlockTags(final RegistrateTagsProvider<Block> provIn) {
            final TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
            // prov.tag(Blocks.EXAMPLE);

        }
    }

    public static class Items {
        //public static final TagKey<Item> EXAMPLE = create("example");

        private static TagKey<Item> create(final String path) {
            return TagKey.create(Registries.ITEM, Mired.path(path));
        }

        protected static void addGenerators() {
            Mired.getRegistrate().addDataGenerator(ProviderType.ITEM_TAGS, Items::genItemTags);
        }

        private static void genItemTags(final RegistrateTagsProvider<Item> provIn) {
            final TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);
            /*
            prov.tag(EXAMPLE)
                    .add(SHEARS)
                    .add(AllItems.WRENCH.asItem());
             */
        }
    }

    public static void register() { }
}
