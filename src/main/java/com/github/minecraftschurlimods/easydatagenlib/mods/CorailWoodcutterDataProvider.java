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

    public static class Sawing extends CorailWoodcutterDataProvider<Sawing.Builder> {
        public Sawing(String namespace, DataGenerator generator) {
            super("woodcutting", namespace, generator);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), input, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count) {
                super(id);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output) {
                this(id, input, output, 1);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output, int count) {
                this(id, input, itemId(output), count);
            }

            public Builder(ResourceLocation id, Ingredient input, Item output) {
                this(id, input, output, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("ingredient", input.toJson());
                json.addProperty("result", output.item.toString());
                json.addProperty("count", output.count);
            }
        }
    }
}
