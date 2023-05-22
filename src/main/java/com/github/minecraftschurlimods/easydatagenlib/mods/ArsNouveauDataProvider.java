package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class ArsNouveauDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ArsNouveauDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("ars_nouveau", folder), namespace, generator);
    }

    public static class Crushing extends ArsNouveauDataProvider<Crushing.Builder> {
        public Crushing(String namespace, DataGenerator generator) {
            super("crush", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param input The input item to use.
         */
        public Builder builder(String id, Ingredient input) {
            return new Builder(new ResourceLocation(namespace, id), input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Pair<PotentiallyAbsentItemStack, Integer>> output = new ArrayList<>();
            private final Ingredient input;
            private boolean skipBlockPlace = false;

            public Builder(ResourceLocation id, Ingredient input) {
                super(id);
                this.input = input;
            }

            /**
             * Sets this recipe to skip block placing.
             *
             * @return This builder, for chaining.
             */
            public Builder skipBlockPlace() {
                skipBlockPlace = true;
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The result item to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(Item item) {
                return addOutput(item, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The result item to use.
             * @param count The result count to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The result item to use.
             * @param count  The result count to use.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addOutput(Item item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item     The result item to use.
             * @param count    The result count to use.
             * @param chance   The chance that the result stack is returned.
             * @param maxRange The max range to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(Item item, int count, float chance, int maxRange) {
                return addOutput(itemId(item), count, chance, maxRange);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The result item to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(ResourceLocation item) {
                return addOutput(item, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The result item to use.
             * @param count The result count to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(ResourceLocation item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The result item to use.
             * @param count  The result count to use.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addOutput(ResourceLocation item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item     The result item to use.
             * @param count    The result count to use.
             * @param chance   The chance that the result stack is returned.
             * @param maxRange The max range to use.
             * @return This builder, for chaining.
             */
            public Builder addOutput(ResourceLocation item, int count, float chance, int maxRange) {
                output.add(Pair.of(new PotentiallyAbsentItemStack.WithChance(item, count, chance), maxRange)); // doesn't support NBT
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("output", JsonUtil.toList(output, e -> {
                    JsonObject o = e.getFirst().toJson();
                    o.addProperty("maxRange", e.getSecond());
                    return o;
                }));
                json.addProperty("skip_block_place", skipBlockPlace);
            }
        }
    }

    public static class Glyph extends ArsNouveauDataProvider<Glyph.Builder> {
        public Glyph(String namespace, DataGenerator generator) {
            super("glyph", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The id of the result item to use.
         * @param count The result count to use.
         */
        public Glyph.Builder builder(String id, ResourceLocation item, int count) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id   The id to use.
         * @param item The id of the result item to use.
         */
        public Glyph.Builder builder(String id, ResourceLocation item) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The result item to use.
         * @param count The result count to use.
         */
        public Glyph.Builder builder(String id, Item item, int count) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id   The id to use.
         * @param item The result item to use.
         */
        public Glyph.Builder builder(String id, Item item) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> inputItems = new ArrayList<>();
            private final PotentiallyAbsentItemStack output;
            private int experience = 0;

            public Builder(ResourceLocation id, ResourceLocation item, int count) {
                super(id);
                output = new PotentiallyAbsentItemStack(item, count); // doesn't support NBT
            }

            public Builder(ResourceLocation id, ResourceLocation item) {
                this(id, item, 1);
            }

            public Builder(ResourceLocation id, Item item, int count) {
                this(id, itemId(item), count);
            }

            public Builder(ResourceLocation id, Item item) {
                this(id, item, 1);
            }

            /**
             * Sets the experience to use in the recipe.
             *
             * @param experience The experience to use.
             * @return This builder, for chaining.
             */
            public Builder setExperience(int experience) {
                this.experience = experience;
                return this;
            }

            /**
             * Adds an input item to the recipe.
             *
             * @param inputItem The input item to add.
             * @return This builder, for chaining.
             */
            public Builder addInputItem(Ingredient inputItem) {
                inputItems.add(inputItem);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                if (this.inputItems.isEmpty())
                    throw new IllegalStateException("Input item list is empty for recipe " + id);
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("exp", experience);
                json.add("inputItems", JsonUtil.toIngredientList(inputItems));
            }
        }
    }

    public static class Imbuement extends ArsNouveauDataProvider<Imbuement.Builder> {
        public Imbuement(String namespace, DataGenerator generator) {
            super("imbuement", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param source The amount of source to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, int source) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, source);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param source The amount of source to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int source) {
            return new Builder(new ResourceLocation(namespace, id), input, output, source);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param source The amount of source to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, int source) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, source);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param source The amount of source to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int source) {
            return new Builder(new ResourceLocation(namespace, id), input, output, source);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> pedestalItems = new ArrayList<>();
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int source;

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count, int source) {
                super(id);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
                this.source = source;
            }

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int source) {
                this(id, input, output, 1, source);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output, int count, int source) {
                this(id, input, itemId(output), count, source);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output, int source) {
                this(id, input, output, 1, source);
            }

            /**
             * Adds a pedestal item to the recipe.
             *
             * @param pedestalItem The pedestal item to add.
             * @return This builder, for chaining.
             */
            public Builder addPedestalItem(Ingredient pedestalItem) {
                pedestalItems.add(pedestalItem);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("source", source);
                json.add("pedestalItems", JsonUtil.toIngredientList(pedestalItems));
            }
        }
    }
}
