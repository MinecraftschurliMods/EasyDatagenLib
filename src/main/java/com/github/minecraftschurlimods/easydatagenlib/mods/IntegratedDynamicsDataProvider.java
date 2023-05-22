package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;

public abstract class IntegratedDynamicsDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected IntegratedDynamicsDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("integrateddynamics", folder), namespace, generator);
    }
    //TODO Drying Basin, Mechanical Drying Basin

    public static class MechanicalSqueezing extends IntegratedDynamicsDataProvider<MechanicalSqueezing.Builder> {
        public MechanicalSqueezing(String namespace, DataGenerator generator) {
            super("mechanical_squeezer", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id       The id to use. Should be unique within the same data provider and the same namespace.
         * @param item     The ingredient to use.
         * @param duration The duration to use.
         */
        public Builder builder(String id, Ingredient item, int duration) {
            return new Builder(new ResourceLocation(namespace, id), item, duration);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> items = new ArrayList<>();
            private final Ingredient item;
            private final int duration;
            private PotentiallyAbsentFluidStack fluid = null;

            /**
             * Creates a new builder with the given id.
             *
             * @param id       The id to use. Should be unique within the same data provider and the same namespace.
             * @param item     The ingredient to use.
             * @param duration The duration to use.
             */
            public Builder(ResourceLocation id, Ingredient item, int duration) {
                super(id);
                this.item = item;
                this.duration = duration;
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The id of the fluid to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid) {
                return setFluid(fluid, 1, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The fluid to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid) {
                return setFluid(fluid, 1, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The id of the fluid to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid, int amount, CompoundTag tag) {
                this.fluid = new PotentiallyAbsentFluidStack(fluid, amount, tag);
                return this;
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The id of the fluid to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid, int amount) {
                return setFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The fluid to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, int amount, CompoundTag tag) {
                return setFluid(fluidId(fluid), amount, tag);
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The fluid to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, int amount) {
                return setFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The fluid to use.
             * @param tag   The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, CompoundTag tag) {
                return setFluid(fluid, 1, tag);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag, float chance) {
                items.add(new PotentiallyAbsentItemStack.WithChance(item, amount, tag, chance));
                return this;
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, float chance) {
                return addItem(item, 1, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag, float chance) {
                return addItem(itemId(item), amount, tag, chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, CompoundTag tag, float chance) {
                return addItem(item, 1, tag, chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag) {
                return addItem(item, amount, tag, 1);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The id of the item to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item) {
                return addItem(item, 1, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag) {
                return addItem(itemId(item), amount, tag);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The item to use.
             * @param tag  The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, CompoundTag tag) {
                return addItem(item, 1, tag);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The item to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item) {
                return addItem(item, 1, new CompoundTag());
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("item", item.toJson());
                json.addProperty("duration", duration);
                JsonObject result = new JsonObject();
                result.add("items", JsonUtil.toList(items));
                if (fluid != null) {
                    result.add("fluid", fluid.toJson());
                }
                json.add("result", result);
            }
        }
    }

    public static class Squeezing extends IntegratedDynamicsDataProvider<Squeezing.Builder> {
        public Squeezing(String namespace, DataGenerator generator) {
            super("squeezer", namespace, generator);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id   The id to use. Should be unique within the same data provider and the same namespace.
         * @param item The ingredient to use.
         */
        public Builder builder(String id, Ingredient item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> items = new ArrayList<>();
            private final Ingredient item;
            private PotentiallyAbsentFluidStack fluid = null;

            /**
             * Creates a new builder with the given id.
             *
             * @param id   The id to use. Should be unique within the same data provider and the same namespace.
             * @param item The ingredient to use.
             */
            public Builder(ResourceLocation id, Ingredient item) {
                super(id);
                this.item = item;
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The id of the fluid to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid, int amount, CompoundTag tag) {
                this.fluid = new PotentiallyAbsentFluidStack(fluid, amount, tag);
                return this;
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The id of the fluid to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid, int amount) {
                return setFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The id of the fluid to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(ResourceLocation fluid) {
                return setFluid(fluid, 1, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The fluid to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, int amount, CompoundTag tag) {
                return setFluid(fluidId(fluid), amount, tag);
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid  The fluid to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, int amount) {
                return setFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The fluid to use.
             * @param tag   The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid, CompoundTag tag) {
                return setFluid(fluid, 1, tag);
            }

            /**
             * Sets the result fluid to use.
             *
             * @param fluid The fluid to use.
             * @return This builder, for chaining.
             */
            public Builder setFluid(Fluid fluid) {
                return setFluid(fluid, 1, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag, float chance) {
                items.add(new PotentiallyAbsentItemStack.WithChance(item, amount, tag, chance));
                return this;
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, float chance) {
                return addItem(item, 1, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag, float chance) {
                return addItem(itemId(item), amount, tag, chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param tag    The NBT tag to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, CompoundTag tag, float chance) {
                return addItem(item, 1, tag, chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param chance The chance for this item to be used.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, new CompoundTag(), chance);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag) {
                return addItem(item, amount, tag, 1);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The id of the item to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The id of the item to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(ResourceLocation item) {
                return addItem(item, 1, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @param tag    The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag) {
                return addItem(itemId(item), amount, tag);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item   The item to use.
             * @param amount The amount to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The item to use.
             * @param tag  The NBT tag to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item, CompoundTag tag) {
                return addItem(item, 1, tag);
            }

            /**
             * Adds a result item to the recipe.
             *
             * @param item The item to use.
             * @return This builder, for chaining.
             */
            public Builder addItem(Item item) {
                return addItem(item, 1, new CompoundTag());
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("item", item.toJson());
                JsonObject result = new JsonObject();
                result.add("items", JsonUtil.toList(items));
                if (fluid != null) {
                    result.add("fluid", fluid.toJson());
                }
                json.add("result", result);
            }
        }
    }
}
