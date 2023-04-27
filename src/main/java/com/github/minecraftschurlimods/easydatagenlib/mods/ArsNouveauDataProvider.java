package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
         * @param id    The id of the recipe builder.
         * @param input The input item to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, Ingredient input) {
            return new Builder(new ResourceLocation(namespace, id), input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Output> output = new ArrayList<>();
            private final Ingredient input;
            private boolean skipBlockPlace = false;

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input to use.
             */
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
                output.add(new Output(new PotentiallyAbsentItemStack.WithChance(item, count, chance), maxRange)); // doesn't support NBT
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                JsonArray output = new JsonArray();
                for (Output o : this.output) {
                    JsonObject object = o.stack.toJson();
                    object.addProperty("maxRange", o.maxRange);
                    output.add(object);
                }
                json.add("output", output);
                json.addProperty("skip_block_place", skipBlockPlace);
            }

            private static final class Output {
                final PotentiallyAbsentItemStack.WithChance stack;
                final int maxRange;

                private Output(PotentiallyAbsentItemStack.WithChance stack) {
                    this(stack, 1);
                }

                private Output(PotentiallyAbsentItemStack.WithChance stack, int maxRange) {
                    this.stack = stack;
                    this.maxRange = maxRange;
                }
            }
        }
    }

    public static class Glyph extends ArsNouveauDataProvider<Glyph.Builder> {
        public Glyph(String namespace, DataGenerator generator) {
            super("glyph", namespace, generator);
        }

        /**
         * @param id   The id of the recipe builder.
         * @param item The result item id to use.
         * @return A new recipe builder.
         */
        public Glyph.Builder builder(String id, ResourceLocation item) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The result item id to use.
         * @param count The result count to use.
         * @return A new recipe builder.
         */
        public Glyph.Builder builder(String id, ResourceLocation item, int count) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * @param id   The id of the recipe builder.
         * @param item The result item to use.
         * @return A new recipe builder.
         */
        public Glyph.Builder builder(String id, Item item) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The result item to use.
         * @param count The result count to use.
         * @return A new recipe builder.
         */
        public Glyph.Builder builder(String id, Item item, int count) {
            return new Glyph.Builder(new ResourceLocation(namespace, id), item, count);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> inputItems = new ArrayList<>();
            private final PotentiallyAbsentItemStack output;
            private int experience = 0;

            /**
             * Creates a new builder with the given id.
             *
             * @param id   The id to use. Should be unique within the same data provider and the same namespace.
             * @param item The result item to use.
             */
            public Builder(ResourceLocation id, Item item) {
                this(id, item, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item to use.
             * @param count The result count to use.
             */
            public Builder(ResourceLocation id, Item item, int count) {
                this(id, itemId(item), count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id   The id to use. Should be unique within the same data provider and the same namespace.
             * @param item The result item id to use.
             */
            public Builder(ResourceLocation id, ResourceLocation item) {
                this(id, item, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             * @param count The result count to use.
             */
            public Builder(ResourceLocation id, ResourceLocation item, int count) {
                super(id);
                output = new PotentiallyAbsentItemStack(item, count); // doesn't support NBT
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
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("exp", experience);
                if (this.inputItems.isEmpty())
                    throw new IllegalStateException("Input item list is empty for recipe " + id);
                JsonArray inputItems = new JsonArray();
                for (Ingredient i : this.inputItems) {
                    JsonObject object = new JsonObject();
                    object.add("item", i.toJson());
                    inputItems.add(object);
                }
                json.add("inputItems", inputItems);
            }
        }
    }

    public static class Imbuement extends ArsNouveauDataProvider<Imbuement.Builder> {
        public Imbuement(String namespace, DataGenerator generator) {
            super("imbuement", namespace, generator);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> pedestalItems = new ArrayList<>();
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int source;

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param input  The input item to use.
             * @param output The output item id to use.
             * @param source The amount of source to use.
             */
            public Builder(ResourceLocation id, Ingredient input, Item output, int source) {
                this(id, input, output, 1, source);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param input  The input item to use.
             * @param output The output item id to use.
             * @param count  The output count to use.
             * @param source The amount of source to use.
             */
            public Builder(ResourceLocation id, Ingredient input, Item output, int count, int source) {
                this(id, input, itemId(output), count, source);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param input  The input item to use.
             * @param output The output item id to use.
             * @param source The amount of source to use.
             */
            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int source) {
                this(id, input, output, 1, source);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id     The id to use. Should be unique within the same data provider and the same namespace.
             * @param input  The input item to use.
             * @param output The output item id to use.
             * @param count  The output count to use.
             * @param source The amount of source to use.
             */
            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count, int source) {
                super(id);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
                this.source = source;
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
                JsonArray pedestalItems = new JsonArray();
                for (Ingredient i : this.pedestalItems) {
                    JsonObject object = new JsonObject();
                    object.add("item", i.toJson());
                    pedestalItems.add(object);
                }
                json.add("pedestalItems", pedestalItems);
            }
        }
    }
}
