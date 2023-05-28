package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class ArsNouveauDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ArsNouveauDataProvider(String folder, String namespace, PackOutput output) {
        super(new ResourceLocation("ars_nouveau", folder), namespace, output);
    }

    public static class Crushing extends ArsNouveauDataProvider<Crushing.Builder> {
        public Crushing(String namespace, PackOutput output) {
            super("crush", namespace, output);
        }

        /**
         * @param id    The recipe id to use.
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
             * Sets this recipe's skipBlockPlace property to true.
             */
            public Builder skipBlockPlace() {
                skipBlockPlace = true;
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item     The output item to use.
             * @param count    The output count to use.
             * @param chance   The chance that this output will be used.
             * @param maxRange The max range to use.
             */
            public Builder addOutput(Item item, int count, float chance, int maxRange) {
                return addOutput(itemId(item), count, chance, maxRange);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(Item item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The output item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addOutput(Item item) {
                return addOutput(item, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item     The output item to use.
             * @param count    The output count to use.
             * @param chance   The chance that this output will be used.
             * @param maxRange The max range to use.
             */
            public Builder addOutput(ResourceLocation item, int count, float chance, int maxRange) {
                output.add(Pair.of(new PotentiallyAbsentItemStack.WithChance(item, count, chance), maxRange)); // doesn't support NBT
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(ResourceLocation item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The output item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(ResourceLocation item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addOutput(ResourceLocation item) {
                return addOutput(item, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("output", JsonUtil.toList(output, e -> {
                    JsonObject o = e.getFirst().toJson();
                    if (!o.has("chance")) {
                        o.addProperty("chance", 1f);
                    }
                    o.addProperty("maxRange", e.getSecond());
                    return o;
                }));
                json.addProperty("skip_block_place", skipBlockPlace);
            }
        }
    }

    public static class Glyph extends ArsNouveauDataProvider<Glyph.Builder> {
        public Glyph(String namespace, PackOutput output) {
            super("glyph", namespace, output);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The id of the output item to use.
         * @param count The output count to use.
         */
        public Builder builder(String id, ResourceLocation item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * @param id   The recipe id to use.
         * @param item The id of the output item to use.
         */
        public Builder builder(String id, ResourceLocation item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The output item to use.
         * @param count The output count to use.
         */
        public Builder builder(String id, Item item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * @param id   The recipe id to use.
         * @param item The output item to use.
         */
        public Builder builder(String id, Item item) {
            return new Builder(new ResourceLocation(namespace, id), item);
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
             * Sets the amount of experience this recipe awards.
             *
             * @param experience The amount of experience this recipe awards.
             */
            public Builder setExperience(int experience) {
                this.experience = experience;
                return this;
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(Ingredient input) {
                inputItems.add(input);
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

    public static class Imbueing extends ArsNouveauDataProvider<Imbueing.Builder> {
        public Imbueing(String namespace, PackOutput output) {
            super("imbuement", namespace, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param mana   The amount of mana to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, int mana) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, mana);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param mana   The amount of mana to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int mana) {
            return new Builder(new ResourceLocation(namespace, id), input, output, mana);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param mana   The amount of mana to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, int mana) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, mana);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input item to use.
         * @param output The id of the output item to use.
         * @param mana   The amount of mana to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int mana) {
            return new Builder(new ResourceLocation(namespace, id), input, output, mana);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> secondaryInputs = new ArrayList<>();
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int mana;

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count, int mana) {
                super(id);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
                this.mana = mana;
            }

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int mana) {
                this(id, input, output, 1, mana);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output, int count, int mana) {
                this(id, input, itemId(output), count, mana);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output, int mana) {
                this(id, input, output, 1, mana);
            }

            /**
             * Adds a secondary input to this recipe.
             *
             * @param secondaryInput The secondary input to add.
             */
            public Builder addSecondaryIngredient(Ingredient secondaryInput) {
                secondaryInputs.add(secondaryInput);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("source", mana);
                json.add("pedestalItems", JsonUtil.toIngredientList(secondaryInputs));
            }
        }
    }
}
