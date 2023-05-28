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
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output ingredient to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient output, int count) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output ingredient to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient output) {
            return new Builder(new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final Ingredient output;
            private final int count;
            private int duration = 200;
            private int minTier = -1;
            private boolean ignoreCrushingMultiplier = false;

            public Builder(ResourceLocation id, Ingredient input, Ingredient output, int count) {
                super(id);
                this.input = input;
                this.output = output;
                this.count = count;
            }

            public Builder(ResourceLocation id, Ingredient input, Ingredient output) {
                this(id, input, output, 1);
            }

            /**
             * Sets the duration of this recipe.
             *
             * @param duration The duration to use.
             */
            public Builder setDuration(int duration) {
                this.duration = duration;
                return this;
            }

            /**
             * Sets the min tier of this recipe.
             *
             * @param minTier The min tier to use.
             */
            public Builder setMinTier(int minTier) {
                this.minTier = minTier;
                return this;
            }

            /**
             * Sets this recipe's ignoreCrushingMultiplier property to true.
             */
            public Builder ignoreCrushingMultiplier() {
                ignoreCrushingMultiplier = true;
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("crushing_time", duration);
                if (minTier > -1) {
                    json.addProperty("min_tier", minTier);
                }
                if (ignoreCrushingMultiplier) {
                    json.addProperty("ignore_crushing_multiplier", true);
                }
                json.add("ingredient", input.toJson());
                JsonObject output = this.output.toJson().getAsJsonObject();
                output.addProperty("count", count);
                json.add("result", output);
            }
        }
    }
}
