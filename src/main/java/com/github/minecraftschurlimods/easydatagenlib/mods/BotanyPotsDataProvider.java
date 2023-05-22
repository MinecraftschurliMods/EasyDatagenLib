package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.DisplayState;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.HarvestEntry;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class BotanyPotsDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected BotanyPotsDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("botanypots", folder), namespace, generator);
    }
    //TODO Fertilizer, Pot Interaction

    public static class Crop extends BotanyPotsDataProvider<Crop.Builder> {
        public Crop(String namespace, DataGenerator generator) {
            super("crop", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id          The id to use.
         * @param seed        The seed ingredient to use.
         * @param growthTicks The time this recipe takes to complete.
         */
        public Builder builder(String id, Ingredient seed, int growthTicks) {
            return new Builder(new ResourceLocation(namespace, id), seed, growthTicks);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final List<HarvestEntry> drops = new ArrayList<>();
            private final List<DisplayState> display = new ArrayList<>();
            private final Ingredient seed;
            private final int growthTicks;
            private int lightLevel = 0;

            public Builder(ResourceLocation id, Ingredient seed, int growthTicks) {
                super(id);
                this.seed = seed;
                this.growthTicks = growthTicks;
            }

            /**
             * Sets the crop's light level.
             *
             * @param lightLevel The light level to set.
             * @return This builder, for chaining.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a soil category to the recipe.
             *
             * @param category The soil category to add.
             * @return This builder, for chaining.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop     The id of the drop item to add.
             * @param chance   The chance of this drop to occur.
             * @param minRolls The min rolls of this drop.
             * @param maxRolls The max rolls of this drop.
             * @return This builder, for chaining.
             */
            public Builder addDrop(ResourceLocation drop, float chance, int minRolls, int maxRolls) {
                drops.add(new HarvestEntry(drop, chance, minRolls, maxRolls));
                return this;
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop     The drop item to add.
             * @param chance   The chance of this drop to occur.
             * @param minRolls The min rolls of this drop.
             * @param maxRolls The max rolls of this drop.
             * @return This builder, for chaining.
             */
            public Builder addDrop(Item drop, float chance, int minRolls, int maxRolls) {
                return addDrop(itemId(drop), chance, minRolls, maxRolls);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop     The id of the drop item to add.
             * @param minRolls The min rolls of this drop.
             * @param maxRolls The max rolls of this drop.
             * @return This builder, for chaining.
             */
            public Builder addDrop(ResourceLocation drop, int minRolls, int maxRolls) {
                return addDrop(drop, 1, minRolls, maxRolls);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop     The drop item to add.
             * @param minRolls The min rolls of this drop.
             * @param maxRolls The max rolls of this drop.
             * @return This builder, for chaining.
             */
            public Builder addDrop(Item drop, int minRolls, int maxRolls) {
                return addDrop(drop, 1, minRolls, maxRolls);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop The id of the drop item to add.
             * @param chance   The chance of this drop to occur.
             * @return This builder, for chaining.
             */
            public Builder addDrop(ResourceLocation drop, float chance) {
                return addDrop(drop, chance, 1, 1);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop The drop item to add.
             * @param chance   The chance of this drop to occur.
             * @return This builder, for chaining.
             */
            public Builder addDrop(Item drop, float chance) {
                return addDrop(drop, chance, 1, 1);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop The id of the drop item to add.
             * @return This builder, for chaining.
             */
            public Builder addDrop(ResourceLocation drop) {
                return addDrop(drop, 1);
            }

            /**
             * Adds a drop to the recipe.
             *
             * @param drop The drop item to add.
             * @return This builder, for chaining.
             */
            public Builder addDrop(Item drop) {
                return addDrop(drop, 1);
            }

            /**
             * Adds a {@link DisplayState} to the recipe.
             *
             * @param display The {@link DisplayState} to add.
             * @return This builder, for chaining.
             */
            public Builder addDisplay(DisplayState display) {
                this.display.add(display);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("seed", seed.toJson());
                json.addProperty("growthTicks", growthTicks);
                json.add("display", JsonUtil.singleOrArray(JsonUtil.toList(display)));
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
                json.add("drops", JsonUtil.toList(drops));
            }
        }
    }

    public static class Soil extends BotanyPotsDataProvider<Soil.Builder> {
        public Soil(String namespace, DataGenerator generator) {
            super("soil", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id      The id to use.
         * @param input   The input ingredient to use.
         * @param display The {@link DisplayState} to use.
         */
        public Builder builder(String id, Ingredient input, DisplayState display) {
            return new Builder(new ResourceLocation(namespace, id), input, display);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final Ingredient input;
            private final DisplayState display;
            private float growthModifier = 1;
            private int lightLevel = 0;

            public Builder(ResourceLocation id, Ingredient input, DisplayState display) {
                super(id);
                this.input = input;
                this.display = display;
            }

            /**
             * Sets the soil's growth modifier.
             *
             * @param growthModifier The growth modifier to set.
             * @return This builder, for chaining.
             */
            public Builder setGrowthModifier(float growthModifier) {
                this.growthModifier = growthModifier;
                return this;
            }

            /**
             * Sets the soil's light level.
             *
             * @param lightLevel The light level to set.
             * @return This builder, for chaining.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a soil category to the soil.
             *
             * @param category The soil category to add.
             * @return This builder, for chaining.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.addProperty("growthModifier", growthModifier);
                json.add("display", display.toJson());
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
            }
        }
    }
}
