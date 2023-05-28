package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.FluidIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.thermal.IngredientWithCount;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;

public abstract class ThermalDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ThermalDataProvider(String folder, String namespace, PackOutput output) {
        super(new ResourceLocation("thermal", folder), namespace, output);
    }
    //TODO Fuels, Fisher Boost, Hive Extractor, Potion Diffuser Boost, Rock Gen Mapping, Tree Extractor Boost, Tree Extractor Mapping

    public static class Bottling extends Processing {
        public Bottling(String namespace, PackOutput output) {
            super("bottler", namespace, output);
        }
    }

    public static class Brewing extends Processing {
        public Brewing(String namespace, PackOutput output) {
            super("brewer", namespace, output);
        }
    }

    public static class Centrifuging extends Processing {
        public Centrifuging(String namespace, PackOutput output) {
            super("centrifuge", namespace, output);
        }
    }

    public static class Chilling extends Processing {
        public Chilling(String namespace, PackOutput output) {
            super("chiller", namespace, output);
        }
    }

    public static class Crucible extends Processing {
        public Crucible(String namespace, PackOutput output) {
            super("crucible", namespace, output);
        }
    }

    public static class Crystallizing extends Processing {
        public Crystallizing(String namespace, PackOutput output) {
            super("crystallizer", namespace, output);
        }
    }

    public static class Furnace extends Processing {
        public Furnace(String namespace, PackOutput output) {
            super("furnace", namespace, output);
        }
    }

    public static class Insolating extends Processing {
        public Insolating(String namespace, PackOutput output) {
            super("insolator", namespace, output);
        }

        /**
         * @param id         The recipe id to use.
         * @param experience The amount of experience this recipe awards.
         */
        public Builder builder(String id, float experience) {
            return new Builder(new ResourceLocation(namespace, id), experience);
        }

        /**
         * @param id The recipe id to use.
         */
        public Builder builder(String id) {
            return new Builder(new ResourceLocation(namespace, id));
        }

        public static class Builder extends Processing.Builder {
            private Float waterModifier;

            public Builder(ResourceLocation id, float experience) {
                super(id, experience);
            }

            public Builder(ResourceLocation id) {
                super(id);
            }

            /**
             * Sets the water modifier to use.
             *
             * @param waterModifier The water modifier to use.
             */
            public Builder setWaterModifier(float waterModifier) {
                this.waterModifier = waterModifier;
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                super.toJson(json);
                if (waterModifier != null) {
                    json.addProperty("water_mod", waterModifier);
                }
            }
        }
    }

    public static class Pressing extends Processing {
        public Pressing(String namespace, PackOutput output) {
            super("press", namespace, output);
        }
    }

    public static class PulverizerRecycling extends Processing {
        public PulverizerRecycling(String namespace, PackOutput output) {
            super("pulverizer_recycle", namespace, output);
        }
    }

    public static class Pulverizing extends Processing {
        public Pulverizing(String namespace, PackOutput output) {
            super("pulverizer", namespace, output);
        }
    }

    public static class Pyrolyzing extends Processing {
        public Pyrolyzing(String namespace, PackOutput output) {
            super("pyrolyzer", namespace, output);
        }
    }

    public static class Refining extends Processing {
        public Refining(String namespace, PackOutput output) {
            super("refinery", namespace, output);
        }
    }

    public static class Sawing extends Processing {
        public Sawing(String namespace, PackOutput output) {
            super("sawmill", namespace, output);
        }
    }

    public static class SmelterRecycling extends Processing {
        public SmelterRecycling(String namespace, PackOutput output) {
            super("smelter_recycle", namespace, output);
        }
    }

    public static class Smelting extends Processing {
        public Smelting(String namespace, PackOutput output) {
            super("smelter", namespace, output);
        }
    }

    /**
     * Note: Not all recipes can actually handle all information. However, Thermal's recipe specification allows everything to be specified for all recipes.
     * {@see https://github.com/CoFH/ThermalCore/blob/1.19.x/src/main/java/cofh/thermal/lib/util/recipes/MachineRecipeSerializer.java}
     */
    public static abstract class Processing extends ThermalDataProvider<Processing.Builder> {
        protected Processing(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        /**
         * @param id         The recipe id to use.
         * @param experience The amount of experience this recipe awards.
         */
        public Builder builder(String id, float experience) {
            return new Builder(new ResourceLocation(namespace, id), experience);
        }

        /**
         * @param id The recipe id to use.
         */
        public Builder builder(String id) {
            return new Builder(new ResourceLocation(namespace, id));
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> inputItems = new ArrayList<>();
            private final List<FluidIngredient> inputFluids = new ArrayList<>();
            private final List<PotentiallyAbsentItemStack> outputItems = new ArrayList<>();
            private final List<PotentiallyAbsentFluidStack> outputFluids = new ArrayList<>();
            private final float experience;
            private Integer energy;
            private Float energyModifier;

            public Builder(ResourceLocation id, float experience) {
                super(id);
                this.experience = experience;
            }

            public Builder(ResourceLocation id) {
                this(id, 0);
            }

            /**
             * Sets the energy of this recipe.
             *
             * @param energy The amount of energy to use.
             */
            public Builder setEnergy(int energy) {
                this.energy = energy;
                return this;
            }

            /**
             * Sets the energy modifier of this recipe.
             *
             * @param energyModifier The energy modifier to use.
             */
            public Builder setEnergyModifier(float energyModifier) {
                this.energyModifier = energyModifier;
                return this;
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(Ingredient input, int count) {
                inputItems.add(new IngredientWithCount(input, count));
                return this;
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(Ingredient input) {
                return addInput(input, 1);
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(FluidIngredient input) {
                inputFluids.add(input);
                return this;
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(ResourceLocation output, int count, CompoundTag tag, float chance) {
                outputItems.add(new PotentiallyAbsentItemStack.WithChance(output, count, tag, chance));
                return this;
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(ResourceLocation output, int count, float chance) {
                return addOutputItem(output, count, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(ResourceLocation output, float chance) {
                return addOutputItem(output, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(Item output, int count, CompoundTag tag, float chance) {
                return addOutputItem(itemId(output), count, tag, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(Item output, int count, float chance) {
                return addOutputItem(output, count, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputItem(Item output, float chance) {
                return addOutputItem(output, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addOutputItem(ResourceLocation output, int count, CompoundTag tag) {
                return addOutputItem(output, count, tag, 1f);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             * @param count  The output count to use.
             */
            public Builder addOutputItem(ResourceLocation output, int count) {
                return addOutputItem(output, count, 1f);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The id of the output item to use.
             */
            public Builder addOutputItem(ResourceLocation output) {
                return addOutputItem(output, 1f);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addOutputItem(Item output, int count, CompoundTag tag) {
                return addOutputItem(itemId(output), count, tag, 1f);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             * @param count  The output count to use.
             */
            public Builder addOutputItem(Item output, int count) {
                return addOutputItem(output, count, 1f);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param output The output item to use.
             */
            public Builder addOutputItem(Item output) {
                return addOutputItem(output, 1f);
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The id of the output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addOutputFluid(ResourceLocation output, int amount, CompoundTag tag) {
                outputFluids.add(new PotentiallyAbsentFluidStack(output, amount, tag));
                return this;
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The id of the output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder addOutputFluid(ResourceLocation output, int amount) {
                return addOutputFluid(output, amount, new CompoundTag());
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The id of the output fluid to use.
             */
            public Builder addOutputFluid(ResourceLocation output) {
                return addOutputFluid(output, 1);
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addOutputFluid(Fluid output, int amount, CompoundTag tag) {
                return addOutputFluid(fluidId(output), amount, tag);
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder addOutputFluid(Fluid output, int amount) {
                return addOutputFluid(output, amount, new CompoundTag());
            }

            /**
             * Adds an output fluid to this recipe.
             *
             * @param output The output fluid to use.
             */
            public Builder addOutputFluid(Fluid output) {
                return addOutputFluid(output, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                if (experience != 0) {
                    json.addProperty("experience", experience);
                }
                if (energy != null) {
                    json.addProperty("energy", energy);
                }
                if (energyModifier != null) {
                    json.addProperty("energy_mod", energyModifier);
                }
                JsonArray input = JsonUtil.toList(inputItems);
                for (FluidIngredient ingredient : inputFluids) {
                    JsonObject fluid = ingredient.toJson();
                    if (fluid.has("tag")) {
                        fluid.add("fluid_tag", fluid.get("tag"));
                        fluid.remove("tag");
                    }
                    input.add(fluid);
                }
                json.add("input", input);
                json.add("output", JsonUtil.mergeArrays(JsonUtil.toList(outputItems), JsonUtil.toList(outputFluids)));
            }
        }
    }
}
