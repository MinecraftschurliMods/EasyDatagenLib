package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class CorailWoodcutterDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected CorailWoodcutterDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("corail_woodcutter", folder), namespace, generator);
    }

    public static class Woodcutting extends CorailWoodcutterDataProvider<Woodcutting.Builder> {
        public Woodcutting(String namespace, DataGenerator generator) {
            super("woodcutting", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use. Should be unique within the same data provider and the same namespace.
         * @param ingredient The ingredient to use.
         * @param result     The id of the result item to use.
         * @param count      The result count to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation result, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use. Should be unique within the same data provider and the same namespace.
         * @param ingredient The ingredient to use.
         * @param result     The result item to use.
         * @param count      The result count to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item result, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use. Should be unique within the same data provider and the same namespace.
         * @param ingredient The ingredient to use.
         * @param result     The id of the result item to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation result) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use. Should be unique within the same data provider and the same namespace.
         * @param ingredient The ingredient to use.
         * @param result     The result item to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item result) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, result);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient ingredient;
            private final PotentiallyAbsentItemStack result;

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient The ingredient to use.
             * @param result     The id of the result item to use.
             * @param count      The result count to use.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation result, int count) {
                super(id);
                this.ingredient = ingredient;
                this.result = new PotentiallyAbsentItemStack(result, count); // doesn't support NBT
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient The ingredient to use.
             * @param result     The result item to use.
             * @param count      The result count to use.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item result, int count) {
                this(id, ingredient, itemId(result), count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient The ingredient to use.
             * @param result     The id of the result item to use.
            */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation result) {
                this(id, ingredient, result, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient The ingredient to use.
             * @param result     The result item to use.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item result) {
                this(id, ingredient, result, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("ingredient", ingredient.toJson());
                json.addProperty("result", result.item.toString());
                json.addProperty("count", result.count);
            }
        }
    }
}
