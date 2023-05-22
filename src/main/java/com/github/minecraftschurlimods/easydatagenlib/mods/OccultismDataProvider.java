package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class OccultismDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected OccultismDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("occultism", folder), namespace, generator);
    }
    //TODO Miner, Ritual, Spirit Fire, Spirit Trade

    public static class Crushing extends OccultismDataProvider<Crushing.Builder> {
        public Crushing(String namespace, DataGenerator generator) {
            super("crushing", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient to use.
         * @param result     The result ingredient to use.
         * @param count      The result count to use.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient result, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient to use.
         * @param result     The result ingredient to use.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient result) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient ingredient;
            private final Ingredient result;
            private final int count;
            private int crushingTime = 200;
            private int minTier = -1;
            private boolean ignoreCrushingMultiplier = false;

            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient result, int count) {
                super(id);
                this.ingredient = ingredient;
                this.result = result;
                this.count = count;
            }

            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient result) {
                this(id, ingredient, result, 1);
            }

            /**
             * Sets the crushing time for this recipe.
             *
             * @param crushingTime The crushing time to use.
             * @return This builder, for chaining.
             */
            public Builder setCrushingTime(int crushingTime) {
                this.crushingTime = crushingTime;
                return this;
            }

            /**
             * Sets the min tier for this recipe.
             *
             * @param minTier The min tier to use.
             * @return This builder, for chaining.
             */
            public Builder setMinTier(int minTier) {
                this.minTier = minTier;
                return this;
            }

            /**
             * Sets this recipe to ignore the crushing multiplier.
             *
             * @return This builder, for chaining.
             */
            public Builder ignoreCrushingMultiplier() {
                ignoreCrushingMultiplier = true;
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("crushing_time", crushingTime);
                if (minTier > -1) {
                    json.addProperty("min_tier", minTier);
                }
                if (ignoreCrushingMultiplier) {
                    json.addProperty("ignore_crushing_multiplier", true);
                }
                json.add("ingredient", ingredient.toJson());
                JsonObject result = this.result.toJson().getAsJsonObject();
                result.addProperty("count", count);
                json.add("result", result);
            }
        }
    }
}
