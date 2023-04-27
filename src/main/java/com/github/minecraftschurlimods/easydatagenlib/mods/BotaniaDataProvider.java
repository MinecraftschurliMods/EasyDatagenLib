package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public abstract class BotaniaDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected BotaniaDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("botania", folder), namespace, generator);
    }
    //TODO Brew, Elven Trade, Orechid, Orechid Ignem, Petal Apothecary, Pure Daisy, Runic Altar, Terra Plate

    public static class ManaInfusion extends BotaniaDataProvider<ManaInfusion.Builder> {
        public ManaInfusion(String namespace, DataGenerator generator) {
            super("mana_infusion", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The id of the output item of this recipe.
         * @param count  The output count of this recipe.
         * @param tag    The output tag of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The id of the output item of this recipe.
         * @param count  The output count of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The id of the output item of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The output item of this recipe.
         * @param count  The output count of this recipe.
         * @param tag    The output tag of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The output item of this recipe.
         * @param count  The output count of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use. Should be unique within the same data provider and the same namespace.
         * @param mana   The amount of mana this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param output The output item of this recipe.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output) {
            return new Builder(new ResourceLocation(namespace, id), mana, input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final int mana;
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private String group;
            private ResourceLocation catalyst;

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The id of the output item of this recipe.
             * @param count  The output count of this recipe.
             * @param tag    The output tag of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, ResourceLocation output, int count, CompoundTag tag) {
                super(id);
                this.mana = mana;
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The id of the output item of this recipe.
             * @param count  The output count of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, ResourceLocation output, int count) {
                this(id, mana, input, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, ResourceLocation output) {
                this(id, mana, input, output, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The output item of this recipe.
             * @param count  The output count of this recipe.
             * @param tag    The output tag of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, Item output, int count, CompoundTag tag) {
                this(id, mana, input, itemId(output), count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The output item of this recipe.
             * @param count  The output count of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, Item output, int count) {
                this(id, mana, input, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param mana   The amount of mana this recipe requires.
             * @param input  The input ingredient of this recipe.
             * @param output The output item of this recipe.
             */
            public Builder(ResourceLocation id, int mana, Ingredient input, Item output) {
                this(id, mana, input, output, 1);
            }

            /**
             * Sets the group of this recipe.
             *
             * @param group The group to set.
             * @return This builder, for chaining.
             */
            public Builder setGroup(String group) {
                this.group = group;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to set.
             * @return This builder, for chaining.
             */
            public Builder setCatalyst(ResourceLocation catalyst) {
                this.catalyst = catalyst;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to set.
             * @return This builder, for chaining.
             */
            public Builder setCatalyst(Block catalyst) {
                return setCatalyst(blockId(catalyst));
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("mana", mana);
                json.add("input", input.toJson());
                json.add("output", output.toJson());
                if (group != null) {
                    json.addProperty("group", group);
                }
                if (catalyst != null) {
                    JsonObject c = new JsonObject();
                    c.addProperty("type", "block");
                    c.addProperty("block", catalyst.toString());
                    json.add("catalyst", c);
                }
            }
        }
    }
}
