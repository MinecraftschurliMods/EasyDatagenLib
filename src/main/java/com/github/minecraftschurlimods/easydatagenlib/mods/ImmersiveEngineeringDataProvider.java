package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.IngredientWithCount;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.ClocheRenderType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class ImmersiveEngineeringDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ImmersiveEngineeringDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("immersiveengineering", folder), namespace, generator);
    }
    //TODO Alloy Furnace, Blast Furnace, Blast Furnace Fuel, Bottling Machine, Cloche Fertilizer, Coke Oven, Fermenter, Generator Fuel, Metal Press, Mineral Mix, Mixer, Refinery, Squeezer, Thermoelectric Source

    public static class ArcFurnace extends ImmersiveEngineeringDataProvider<ArcFurnace.Builder> {
        public ArcFurnace(String namespace, DataGenerator generator) {
            super("arc_furnace", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param time   The time this recipe takes to complete.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param count  The amount of input ingredients required.
         */
        public Builder builder(String id, int time, int energy, Ingredient input, int count) {
            return new Builder(new ResourceLocation(namespace, id), time, energy, input, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param time   The time this recipe takes to complete.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         */
        public Builder builder(String id, int time, int energy, Ingredient input) {
            return new Builder(new ResourceLocation(namespace, id), time, energy, input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> additives = new ArrayList<>();
            private final List<IngredientWithCount> results = new ArrayList<>();
            private final List<IngredientWithCount> secondaries = new ArrayList<>();
            private final int time;
            private final int energy;
            private final IngredientWithCount input;
            private IngredientWithCount slag;

            public Builder(ResourceLocation id, int time, int energy, Ingredient input, int count) {
                super(id);
                this.time = time;
                this.energy = energy;
                this.input = new IngredientWithCount(input, count);
            }

            public Builder(ResourceLocation id, int time, int energy, Ingredient input) {
                this(id, time, energy, input, 1);
            }

            /**
             * Sets the slag output for this recipe.
             *
             * @param slag The slag ingredient for this recipe.
             * @return This builder, for chaining.
             */
            public Builder setSlag(Ingredient slag) {
                return setSlag(slag, 1);
            }

            /**
             * Sets the slag output for this recipe.
             *
             * @param slag  The slag ingredient for this recipe.
             * @param count The amount of slag for this recipe.
             * @return This builder, for chaining.
             */
            public Builder setSlag(Ingredient slag, int count) {
                this.slag = new IngredientWithCount(slag, count);
                return this;
            }

            /**
             * Adds an additive to this recipe.
             *
             * @param additive The additive ingredient to add.
             * @param count    The amount of additives required.
             * @return This builder, for chaining.
             */
            public Builder addAdditive(Ingredient additive, int count) {
                additives.add(new IngredientWithCount(additive, count));
                return this;
            }

            /**
             * Adds an additive to this recipe.
             *
             * @param additive The additive ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addAdditive(Ingredient additive) {
                return addAdditive(additive, 1);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The result ingredient to add.
             * @param count  The amount of results required.
             * @return This builder, for chaining.
             */
            public Builder addResult(Ingredient result, int count) {
                results.add(new IngredientWithCount(result, count));
                return this;
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The result ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addResult(Ingredient result) {
                return addResult(result, 1);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The amount of secondaries required.
             * @param chance    The chance of this secondary ingredient to be used.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, int count, float chance) {
                secondaries.add(new IngredientWithCount.WithChance(secondary, count, chance));
                return this;
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param chance    The chance of this secondary ingredient to be used.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, float chance) {
                return addSecondary(secondary, 1, chance);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The amount of secondaries required.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, int count) {
                return addSecondary(secondary, count, 1);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary) {
                return addSecondary(secondary, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("time", time);
                json.addProperty("energy", energy);
                json.add("input", input.toJson());
                if (slag != null) {
                    json.add("slag", slag.toJson());
                }
                if (results.isEmpty()) throw new IllegalStateException("Results cannot be empty for recipe" + id + "!");
                json.add("results", JsonUtil.toList(results));
                if (!additives.isEmpty()) {
                    json.add("additives", JsonUtil.toList(additives));
                }
                if (!secondaries.isEmpty()) {
                    json.add("secondaries", JsonUtil.toList(secondaries));
                }
            }
        }
    }

    public static class Cloche extends ImmersiveEngineeringDataProvider<Cloche.Builder> {
        public Cloche(String namespace, DataGenerator generator) {
            super("cloche", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param time       The time this recipe requires.
         * @param input      The input ingredient of this recipe.
         * @param soil       The soil ingredient of this recipe.
         * @param renderType The render mode to use in the recipe renderer.
         * @param block      The id of the block to use in the recipe renderer.
         */
        public Builder builder(String id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, ResourceLocation block) {
            return new Builder(new ResourceLocation(namespace, id), time, input, soil, renderType, block);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param time       The time this recipe requires.
         * @param input      The input ingredient of this recipe.
         * @param soil       The soil ingredient of this recipe.
         * @param renderType The render mode to use in the recipe renderer.
         * @param block      The block to use in the recipe renderer.
         */
        public Builder builder(String id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, Block block) {
            return new Builder(new ResourceLocation(namespace, id), time, input, soil, renderType, block);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> results = new ArrayList<>();
            private final int time;
            private final Ingredient input;
            private final Ingredient soil;
            private final ClocheRenderType renderType;
            private final ResourceLocation block;

            public Builder(ResourceLocation id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, ResourceLocation block) {
                super(id);
                this.time = time;
                this.input = input;
                this.soil = soil;
                this.renderType = renderType;
                this.block = block;
            }

            public Builder(ResourceLocation id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, Block block) {
                this(id, time, input, soil, renderType, blockId(block));
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The result ingredient to add.
             * @param count  The amount of results required.
             * @return This builder, for chaining.
             */
            public Builder addResult(Ingredient result, int count) {
                results.add(new IngredientWithCount(result, count));
                return this;
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The result ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addResult(Ingredient result) {
                return addResult(result, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("time", time);
                json.add("input", input.toJson());
                json.add("soil", soil.toJson());
                json.add("results", JsonUtil.toList(results));
                JsonObject render = new JsonObject();
                render.addProperty("type", renderType.toString());
                render.addProperty("block", block.toString());
                json.add("render", render);
            }
        }
    }

    public static class Crusher extends ImmersiveEngineeringDataProvider<Crusher.Builder> {
        public Crusher(String namespace, DataGenerator generator) {
            super("crusher", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The result ingredient of this recipe.
         * @param count  The result count of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, Ingredient result, int count) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The result ingredient of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, Ingredient result) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> secondaries = new ArrayList<>();
            private final int energy;
            private final Ingredient input;
            private final IngredientWithCount result;

            public Builder(ResourceLocation id, int energy, Ingredient input, Ingredient result, int count) {
                super(id);
                this.energy = energy;
                this.input = input;
                this.result = new IngredientWithCount(result, count);
            }

            public Builder(ResourceLocation id, int energy, Ingredient input, Ingredient result) {
                this(id, energy, input, result, 1);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The amount of secondaries required.
             * @param chance    The chance of this secondary ingredient to be used.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, int count, float chance) {
                secondaries.add(new IngredientWithCount.WithChance(secondary, count, chance));
                return this;
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param chance    The chance of this secondary ingredient to be used.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, float chance) {
                return addSecondary(secondary, 1, chance);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The amount of secondaries required.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, int count) {
                return addSecondary(secondary, count, 1);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary) {
                return addSecondary(secondary, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("energy", energy);
                json.add("input", input.toJson());
                json.add("result", result.toJson());
                json.add("secondaries", JsonUtil.toList(secondaries));
            }
        }
    }

    public static class Sawmill extends ImmersiveEngineeringDataProvider<Sawmill.Builder> {
        public Sawmill(String namespace, DataGenerator generator) {
            super("sawmill", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The id of the result item of this recipe.
         * @param count  The result count of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, ResourceLocation result, int count) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The id of the result item of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, ResourceLocation result) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The result item of this recipe.
         * @param count  The result count of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, Item result, int count) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param energy The amount of energy this recipe requires.
         * @param input  The input ingredient of this recipe.
         * @param result The result item of this recipe.
         */
        public Builder builder(String id, int energy, Ingredient input, Item result) {
            return new Builder(new ResourceLocation(namespace, id), energy, input, result);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Pair<IngredientWithCount, Boolean>> secondaries = new ArrayList<>();
            private final int energy;
            private final Ingredient input;
            private final PotentiallyAbsentItemStack result;
            private IngredientWithCount stripped;

            public Builder(ResourceLocation id, int energy, Ingredient input, ResourceLocation result, int count) {
                super(id);
                this.energy = energy;
                this.input = input;
                this.result = new PotentiallyAbsentItemStack(result, count); // doesn't support NBT
            }

            public Builder(ResourceLocation id, int energy, Ingredient input, ResourceLocation result) {
                this(id, energy, input, result, 1);
            }

            public Builder(ResourceLocation id, int energy, Ingredient input, Item result, int count) {
                this(id, energy, input, itemId(result), count);
            }

            public Builder(ResourceLocation id, int energy, Ingredient input, Item result) {
                this(id, energy, input, result, 1);
            }

            /**
             * Sets the stripping result for this recipe.
             *
             * @param stripped The stripping result to use.
             * @param count The stripping result amount to use.
             */
            public Builder setStripped(Ingredient stripped, int count) {
                this.stripped = new IngredientWithCount(stripped, count);
                return this;
            }

            /**
             * Sets the stripping result for this recipe.
             *
             * @param stripped The stripping result to use.
             */
            public Builder setStripped(Ingredient stripped) {
                return setStripped(stripped, 1);
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The amount of secondaries required.
             * @param stripping Whether this should apply during a stripping or during a non-stripping operation.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, int count, boolean stripping) {
                secondaries.add(Pair.of(new IngredientWithCount(secondary, count), stripping));
                return this;
            }

            /**
             * Adds a secondary result to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param stripping Whether this should apply during a stripping or during a non-stripping operation.
             * @return This builder, for chaining.
             */
            public Builder addSecondary(Ingredient secondary, boolean stripping) {
                return addSecondary(secondary, 1, stripping);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("energy", energy);
                json.add("input", input.toJson());
                json.add("result", result.toJson());
                if (stripped != null) {
                    json.add("stripped", stripped.toJson());
                }
                json.add("secondaries", JsonUtil.toList(secondaries, e -> {
                    JsonObject o = new JsonObject();
                    o.add("output", e.getFirst().toJson());
                    o.addProperty("stripping", e.getSecond());
                    return o;
                }));
            }
        }
    }
}
