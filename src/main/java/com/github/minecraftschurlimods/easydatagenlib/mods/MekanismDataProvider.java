package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.Chemical;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.Gas;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.InfuseType;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.IngredientWithAmount;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.Pigment;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class MekanismDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected MekanismDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("mekanism", folder), namespace, generator);
    }
    //TODO Chemical Infusing, Compressing, Crystallizing, Dissolution, Energy Conversion, Evaporating, Injecting, Metallurgic Infusing, Nucleosynthesizing, Painting, Pigment Mixing, Purifying, Reaction, Rotary, Separating, Washing

    public static class Activating extends GasToGasRecipe {
        public Activating(String namespace, DataGenerator generator) {
            super("activating", namespace, generator);
        }
    }

    public static class Centrifuging extends GasToGasRecipe {
        public Centrifuging(String namespace, DataGenerator generator) {
            super("centrifuging", namespace, generator);
        }
    }

    public static class Combining extends IngredientToItemRecipe {
        public Combining(String namespace, DataGenerator generator) {
            super("combining", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, extraAmount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param extraAmount     The amount of extra ingredients to use.
         * @param output          The output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, extraAmount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param amount          The amount of ingredients to use.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, extraIngredient, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The output item of this recipe.
         * @param count           The output count of this recipe.
         * @param tag             The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The output item of this recipe.
         * @param count           The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id              The id to use.
         * @param ingredient      The input ingredient of this recipe.
         * @param extraIngredient The extra ingredient of this recipe.
         * @param output          The output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Ingredient extraIngredient, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, extraIngredient, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private final IngredientWithAmount extraInput;

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count, CompoundTag tag) {
                super(id, ingredient, amount, output, count, tag);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count) {
                super(id, ingredient, amount, output, count);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, ResourceLocation output) {
                super(id, ingredient, amount, output);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output, int count, CompoundTag tag) {
                super(id, ingredient, amount, output, count, tag);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output, int count) {
                super(id, ingredient, amount, output, count);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, int extraAmount, Item output) {
                super(id, ingredient, amount, output);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count, CompoundTag tag) {
                super(id, ingredient, output, count, tag);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output, int count) {
                super(id, ingredient, output, count);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, ResourceLocation output) {
                super(id, ingredient, output);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output, int count, CompoundTag tag) {
                super(id, ingredient, output, count, tag);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output, int count) {
                super(id, ingredient, output, count);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param extraAmount     The amount of extra ingredients to use.
             * @param output          The output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, int extraAmount, Item output) {
                super(id, ingredient, output);
                this.extraInput = new IngredientWithAmount(extraIngredient, extraAmount);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output, int count, CompoundTag tag) {
                this(id, ingredient, amount, extraIngredient, 1, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output, int count) {
                this(id, ingredient, amount, extraIngredient, 1, output, count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, ResourceLocation output) {
                this(id, ingredient, amount, extraIngredient, 1, output);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output, int count, CompoundTag tag) {
                this(id, ingredient, amount, extraIngredient, 1, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output, int count) {
                this(id, ingredient, amount, extraIngredient, 1, output, count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param amount          The amount of ingredients to use.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Ingredient extraIngredient, Item output) {
                this(id, ingredient, amount, extraIngredient, 1, output);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output, int count, CompoundTag tag) {
                this(id, ingredient, extraIngredient, 1, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output, int count) {
                this(id, ingredient, extraIngredient, 1, output, count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, ResourceLocation output) {
                this(id, ingredient, extraIngredient, 1, output);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The output item of this recipe.
             * @param count           The output count of this recipe.
             * @param tag             The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, Item output, int count, CompoundTag tag) {
                this(id, ingredient, extraIngredient, 1, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The output item of this recipe.
             * @param count           The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, Item output, int count) {
                this(id, ingredient, extraIngredient, 1, output, count);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id              The id to use.
             * @param ingredient      The input ingredient of this recipe.
             * @param extraIngredient The extra ingredient of this recipe.
             * @param output          The output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Ingredient extraIngredient, Item output) {
                this(id, ingredient, extraIngredient, 1, output);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("mainInput", input.toJson());
                json.add("extraInput", extraInput.toJson());
                json.add("output", output.toJson());
            }
        }
    }

    public static class Crushing extends IngredientToItemRecipe {
        public Crushing(String namespace, DataGenerator generator) {
            super("crushing", namespace, generator);
        }
    }

    public static class Enriching extends IngredientToItemRecipe {
        public Enriching(String namespace, DataGenerator generator) {
            super("enriching", namespace, generator);
        }
    }

    public static class GasConversion extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public GasConversion(String namespace, DataGenerator generator) {
            super("gas_conversion", namespace, generator);
        }
    }

    public static class InfusionConversion extends IngredientToTRecipe<Chemical.Stack<InfuseType>> {
        public InfusionConversion(String namespace, DataGenerator generator) {
            super("infusion_conversion", namespace, generator);
        }
    }

    public static class Oxidizing extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public Oxidizing(String namespace, DataGenerator generator) {
            super("oxidizing", namespace, generator);
        }
    }

    public static class PigmentExtracting extends IngredientToTRecipe<Chemical.Stack<Pigment>> {
        public PigmentExtracting(String namespace, DataGenerator generator) {
            super("pigment_extracting", namespace, generator);
        }
    }

    public static class Sawing extends IngredientToItemRecipe {
        public Sawing(String namespace, DataGenerator generator) {
            super("sawing", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item to use.
         * @param count      The output count to use.
         * @param tag        The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The output item to use.
         * @param count      The output count to use.
         * @param tag        The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param amount     The amount of ingredients to use.
         * @param output     The output item to use.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The id of the output item to use.
         * @param count      The output count to use.
         * @param tag        The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The id of the output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The output item to use.
         * @param count      The output count to use.
         * @param tag        The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The ingredient to use.
         * @param output     The output item to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private PotentiallyAbsentItemStack secondaryOutput = null;
            private float chance = 1;

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             * @param tag        The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output, int count, CompoundTag tag) {
                super(id, ingredient, amount, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output, int count) {
                this(id, ingredient, amount, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output) {
                this(id, ingredient, amount, output, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             * @param tag        The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output, int count, CompoundTag tag) {
                this(id, ingredient, amount, itemId(output), count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output, int count) {
                this(id, ingredient, amount, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param amount     The amount of ingredients to use.
             * @param output     The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output) {
                this(id, ingredient, amount, output, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             * @param tag        The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output, int count, CompoundTag tag) {
                this(id, ingredient, 1, output, count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The id of the output item of this recipe.
             * @param count      The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output, int count) {
                this(id, ingredient, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The id of the output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output) {
                this(id, ingredient, output, 1);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The output item of this recipe.
             * @param count      The output count of this recipe.
             * @param tag        The output tag of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item output, int count, CompoundTag tag) {
                this(id, ingredient, itemId(output), count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The output item of this recipe.
             * @param count      The output count of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item output, int count) {
                this(id, ingredient, output, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id         The id to use.
             * @param ingredient The input ingredient of this recipe.
             * @param output     The output item of this recipe.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item output) {
                this(id, ingredient, output, 1);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output) {
                return setSecondaryOutput(output, 1, new CompoundTag());
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output) {
                return setSecondaryOutput(output, 1, new CompoundTag());
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int count, CompoundTag tag, float chance) {
                this.secondaryOutput = new PotentiallyAbsentItemStack(output, count, tag);
                this.chance = chance;
                return this;
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int count, float chance) {
                return setSecondaryOutput(output, count, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output, float chance) {
                return setSecondaryOutput(output, 1, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, int count, CompoundTag tag, float chance) {
                return setSecondaryOutput(itemId(output), count, tag, chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, int count, float chance) {
                return setSecondaryOutput(output, count, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, CompoundTag tag, float chance) {
                return setSecondaryOutput(output, 1, tag, chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param chance The chance that this output will be used.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, float chance) {
                return setSecondaryOutput(output, 1, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int count, CompoundTag tag) {
                return setSecondaryOutput(output, count, tag, 1);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int count) {
                return setSecondaryOutput(output, count, new CompoundTag());
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, int count, CompoundTag tag) {
                return setSecondaryOutput(itemId(output), count, tag);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, int count) {
                return setSecondaryOutput(output, count, new CompoundTag());
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             * @param tag    The output NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setSecondaryOutput(Item output, CompoundTag tag) {
                return setSecondaryOutput(output, 1, tag);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("mainOutput", output.toJson());
                if (secondaryOutput != null && chance > 0 && chance <= 1) {
                    json.add("secondaryOutput", secondaryOutput.toJson());
                    json.addProperty("secondaryChance", chance);
                }
            }
        }
    }

    public static class Smelting extends IngredientToItemRecipe {
        public Smelting(String namespace, DataGenerator generator) {
            super("smelting", namespace, generator);
        }
    }

    protected abstract static class Abstract1To1Recipe<I extends JsonSerializable, O extends JsonSerializable, B extends Abstract1To1Recipe.Builder<I, O>> extends MekanismDataProvider<B> {
        protected Abstract1To1Recipe(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        public static class Builder<I extends JsonSerializable, O extends JsonSerializable> extends AbstractRecipeBuilder<Builder<I, O>> {
            protected final I input;
            protected final O output;

            protected Builder(ResourceLocation id, I input, O output) {
                super(id);
                this.input = input;
                this.output = output;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("output", output.toJson());
            }
        }
    }

    protected abstract static class IngredientToTRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<IngredientWithAmount, T, IngredientToTRecipe.Builder<T>> {
        protected IngredientToTRecipe(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input ingredient of this recipe.
         * @param amount The output amount of this recipe.
         * @param output The output chemical of this recipe.
         */
        public Builder<T> builder(String id, Ingredient input, int amount, T output) {
            return new Builder<>(new ResourceLocation(namespace, id), input, amount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input ingredient of this recipe.
         * @param output The output chemical of this recipe.
         */
        public Builder<T> builder(String id, Ingredient input, T output) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<IngredientWithAmount, T> {
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, T output) {
                super(id, new IngredientWithAmount(ingredient, amount), output);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, T output) {
                this(id, ingredient, 1, output);
            }
        }
    }

    protected abstract static class TToItemRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<T, PotentiallyAbsentItemStack, TToItemRecipe.Builder<T>> {
        protected TToItemRecipe(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The id of the output item of this recipe.
         * @param count  The output count of this recipe.
         * @param tag    The output tag of this recipe.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The id of the output item of this recipe.
         * @param count  The output count of this recipe.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output, int count) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The id of the output item of this recipe.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The output item of this recipe.
         * @param count  The output count of this recipe.
         * @param tag    The output tag of this recipe.
         */
        public Builder<T> builder(String id, T input, Item output, int count, CompoundTag tag) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The output item of this recipe.
         * @param count  The output count of this recipe.
         */
        public Builder<T> builder(String id, T input, Item output, int count) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input of this recipe.
         * @param output The output item of this recipe.
         */
        public Builder<T> builder(String id, T input, Item output) {
            return new Builder<>(new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<T, PotentiallyAbsentItemStack> {
            public Builder(ResourceLocation id, T input, ResourceLocation output, int count, CompoundTag tag) {
                super(id, input, new PotentiallyAbsentItemStack(output, count, tag));
            }

            public Builder(ResourceLocation id, T input, ResourceLocation output, int count) {
                this(id, input, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, T input, ResourceLocation output) {
                this(id, input, output, 1);
            }

            public Builder(ResourceLocation id, T input, Item output, int count, CompoundTag tag) {
                this(id, input, itemId(output), count, tag);
            }

            public Builder(ResourceLocation id, T input, Item output, int count) {
                this(id, input, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, T input, Item output) {
                this(id, input, output, 1);
            }
        }
    }

    protected abstract static class IngredientToItemRecipe extends Abstract1To1Recipe<IngredientWithAmount, PotentiallyAbsentItemStack, IngredientToItemRecipe.Builder> {
        protected IngredientToItemRecipe(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         * @param tag        The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         * @param tag        The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param amount     The amount of ingredients to use.
         * @param output     The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, int amount, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, amount, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         * @param tag        The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The id of the output item of this recipe.
         * @param count      The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The id of the output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The output item of this recipe.
         * @param count      The output count of this recipe.
         * @param tag        The output tag of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Item output, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The output item of this recipe.
         * @param count      The output count of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Item output, int count) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id         The id to use.
         * @param ingredient The input ingredient of this recipe.
         * @param output     The output item of this recipe.
         */
        public Builder builder(String id, Ingredient ingredient, Item output) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<IngredientWithAmount, PotentiallyAbsentItemStack> {
            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output, int count, CompoundTag tag) {
                super(id, new IngredientWithAmount(ingredient, amount), new PotentiallyAbsentItemStack(output, count, tag));
            }

            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output, int count) {
                this(id, ingredient, amount, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, Ingredient ingredient, int amount, ResourceLocation output) {
                this(id, ingredient, amount, output, 1);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output, int count, CompoundTag tag) {
                this(id, ingredient, amount, itemId(output), count, tag);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output, int count) {
                this(id, ingredient, amount, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, Ingredient ingredient, int amount, Item output) {
                this(id, ingredient, amount, output, 1);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output, int count, CompoundTag tag) {
                this(id, ingredient, 1, output, count, tag);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output, int count) {
                this(id, ingredient, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation output) {
                this(id, ingredient, output, 1);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, Item output, int count, CompoundTag tag) {
                this(id, ingredient, itemId(output), count, tag);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, Item output, int count) {
                this(id, ingredient, output, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, Ingredient ingredient, Item output) {
                this(id, ingredient, output, 1);
            }
        }
    }

    protected abstract static class GasToGasRecipe extends Abstract1To1Recipe<Chemical.Stack<Gas>, Chemical.Stack<Gas>, GasToGasRecipe.Builder> {
        protected GasToGasRecipe(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id     The id to use.
         * @param input  The input gas stack to use.
         * @param output The output gas stack to use.
         */
        public Builder builder(String id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
            return new Builder(new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<Chemical.Stack<Gas>, Chemical.Stack<Gas>> {
            public Builder(ResourceLocation id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
                super(id, input, output);
            }
        }
    }
}
