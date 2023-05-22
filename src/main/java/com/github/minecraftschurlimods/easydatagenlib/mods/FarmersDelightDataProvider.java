package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class FarmersDelightDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected FarmersDelightDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("farmersdelight", folder), namespace, generator);
    }

    public static class Cooking extends FarmersDelightDataProvider<Cooking.Builder> {
        public Cooking(String namespace, DataGenerator generator) {
            super("cooking", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id          The id to use.
         * @param cookingTime The amount of time this recipe takes.
         * @param experience  The amount of experience to award when the recipe is completed.
         * @param result      The id of the result item to use.
         * @param count       The result count to use.
         */
        public Builder builder(String id, int cookingTime, float experience, ResourceLocation result, int count) {
            return new Builder(new ResourceLocation(namespace, id), cookingTime, experience, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id          The id to use.
         * @param cookingTime The amount of time this recipe takes.
         * @param experience  The amount of experience to award when the recipe is completed.
         * @param result      The id of the result item to use.
         */
        public Builder builder(String id, int cookingTime, float experience, ResourceLocation result) {
            return new Builder(new ResourceLocation(namespace, id), cookingTime, experience, result);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id          The id to use.
         * @param cookingTime The amount of time this recipe takes.
         * @param experience  The amount of experience to award when the recipe is completed.
         * @param result      The result item to use.
         * @param count       The result count to use.
         */
        public Builder builder(String id, int cookingTime, float experience, Item result, int count) {
            return new Builder(new ResourceLocation(namespace, id), cookingTime, experience, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id          The id to use.
         * @param cookingTime The amount of time this recipe takes.
         * @param experience  The amount of experience to award when the recipe is completed.
         * @param result      The result item to use.
         */
        public Builder builder(String id, int cookingTime, float experience, Item result) {
            return new Builder(new ResourceLocation(namespace, id), cookingTime, experience, result);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> ingredients = new ArrayList<>();
            private final int cookingTime;
            private final float experience;
            private final PotentiallyAbsentItemStack result;
            private PotentiallyAbsentItemStack container = null;
            private String recipeBookTab = null;

            public Builder(ResourceLocation id, int cookingTime, float experience, ResourceLocation result, int count) {
                super(id);
                this.cookingTime = cookingTime;
                this.experience = experience;
                this.result = new PotentiallyAbsentItemStack(result, count); // doesn't support NBT
            }

            public Builder(ResourceLocation id, int cookingTime, float experience, ResourceLocation result) {
                this(id, cookingTime, experience, result, 1);
            }

            public Builder(ResourceLocation id, int cookingTime, float experience, Item result, int count) {
                this(id, cookingTime, experience, itemId(result), count);
            }

            public Builder(ResourceLocation id, int cookingTime, float experience, Item result) {
                this(id, cookingTime, experience, result, 1);
            }

            /**
             * Sets the container item of this recipe.
             *
             * @param container The id of the container item to use.
             * @return This builder, for chaining.
             */
            public Builder setContainer(ResourceLocation container) {
                this.container = new PotentiallyAbsentItemStack(container);
                return this;
            }

            /**
             * Sets the container item of this recipe.
             *
             * @param container The container item to use.
             * @return This builder, for chaining.
             */
            public Builder setContainer(Item container) {
                return setContainer(itemId(container));
            }

            /**
             * Sets the recipe book tab of this recipe.
             *
             * @param recipeBookTab The recipe book tab to use.
             * @return This builder, for chaining.
             */
            public Builder setRecipeBookTab(String recipeBookTab) {
                this.recipeBookTab = recipeBookTab;
                return this;
            }

            /**
             * Adds an ingredient to this recipe.
             *
             * @param ingredient The ingredient to add.
             * @return This builder, for chaining.
             */
            public Builder addIngredient(Ingredient ingredient) {
                ingredients.add(ingredient);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("cookingtime", cookingTime);
                json.addProperty("experience", experience);
                json.add("result", result.toJson());
                if (recipeBookTab != null) {
                    json.addProperty("recipe_book_tab", recipeBookTab);
                }
                if (container != null) {
                    json.add("container", container.toJson());
                }
                json.add("ingredients", JsonUtil.toIngredientList(ingredients));
            }
        }
    }

    public static class Cutting extends FarmersDelightDataProvider<Cutting.Builder> {
        public Cutting(String namespace, DataGenerator generator) {
            super("cutting", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use.
         * @param ingredient The ingredient to use.
         * @param tool The tool to use.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient tool) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, tool);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> result = new ArrayList<>();
            private final Ingredient ingredient;
            private final Ingredient tool;
            private String sound;

            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient tool) {
                super(id);
                this.ingredient = ingredient;
                this.tool = tool;
            }

            /**
             * Sets the cutting sound.
             *
             * @param sound The sound to set.
             * @return This builder, for chaining.
             */
            public Builder setSound(String sound) {
                this.sound = sound;
                return this;
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item   The id of the item to use.
             * @param count  The count to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance that this result is used.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count, CompoundTag tag, float chance) {
                result.add(new PotentiallyAbsentItemStack.WithChance(item, count, tag, chance));
                return this;
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item  The id of the item to use.
             * @param count The count to use.
             * @param tag   The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count, CompoundTag tag) {
                return addResult(item, count, tag, 1);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item  The id of the item to use.
             * @param count The count to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count) {
                return addResult(item, count, new CompoundTag());
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item The id of the item to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item) {
                return addResult(item, 1);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item   The item to use.
             * @param count  The count to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance that this result is used.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count, CompoundTag tag, float chance) {
                return addResult(itemId(item), count, tag, chance);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item  The item to use.
             * @param count The count to use.
             * @param tag   The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count, CompoundTag tag) {
                return addResult(item, count, tag, 1);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item  The item to use.
             * @param count The count to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count) {
                return addResult(item, count, new CompoundTag());
            }

            /**
             * Adds a result to this recipe.
             *
             * @param item The item to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item) {
                return addResult(item, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("ingredients", JsonUtil.toIngredientList(List.of(ingredient)));
                json.add("tool", tool.toJson());
                if (sound != null) {
                    json.addProperty("sound", sound);
                }
                json.add("result", JsonUtil.toList(result));
            }
        }
    }
}
