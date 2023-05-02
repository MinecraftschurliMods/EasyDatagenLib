package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class ElementalcraftDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ElementalcraftDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("elementalcraft", folder), namespace, generator);
    }
    //TODO Binding, Crystallization, Infusion, Inscription, Pure Infusion, Spell Craft

    public static class Grinding extends IO {
        public Grinding(String namespace, DataGenerator generator) {
            super("grinding", namespace, generator);
        }
    }

    public static class Sawing extends IO {
        public Sawing(String namespace, DataGenerator generator) {
            super("sawing", namespace, generator);
        }
    }

    protected static abstract class IO extends ElementalcraftDataProvider<IO.Builder> {
        protected IO(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The id of the result item to use.
         * @param count The result count to use.
         * @param tag The result NBT to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, tag, elementAmount, luckRatio);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The id of the result item to use.
         * @param count The result count to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, new CompoundTag(), elementAmount, luckRatio);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The id of the result item to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The result item to use.
         * @param count The result count to use.
         * @param tag The result NBT to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, itemId(output), count, tag, elementAmount, luckRatio);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The result item to use.
         * @param count The result count to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, output, count, new CompoundTag(), elementAmount, luckRatio);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id The id to use. Should be unique within the same data provider and the same namespace.
         * @param input The input ingredient to use.
         * @param output The result item to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int elementAmount, int luckRatio) {
            return new Builder(new ResourceLocation(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int elementAmount;
            private final int luckRatio;

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The id of the result item to use.
             * @param count The result count to use.
             * @param tag The result NBT to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
                super(id);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, tag);
                this.elementAmount = elementAmount;
                this.luckRatio = luckRatio;
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The id of the result item to use.
             * @param count The result count to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int count, int elementAmount, int luckRatio) {
                this(id, input, output, count, new CompoundTag(), elementAmount, luckRatio);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The id of the result item to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, ResourceLocation output, int elementAmount, int luckRatio) {
                this(id, input, output, 1, elementAmount, luckRatio);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The result item to use.
             * @param count The result count to use.
             * @param tag The result NBT to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, Item output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
                this(id, input, itemId(output), count, tag, elementAmount, luckRatio);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The result item to use.
             * @param count The result count to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
                this(id, input, output, count, new CompoundTag(), elementAmount, luckRatio);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id The id to use. Should be unique within the same data provider and the same namespace.
             * @param input The input ingredient to use.
             * @param output The result item to use.
             * @param elementAmount The element amount to use.
             * @param luckRatio The luck ratio to use.
             */
            public Builder(ResourceLocation id, Ingredient input, Item output, int elementAmount, int luckRatio) {
                this(id, input, output, 1, elementAmount, luckRatio);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("output", output.toJson());
                json.addProperty("element_amount", elementAmount);
                json.addProperty("luck_ratio", luckRatio);
            }
        }
    }
}
