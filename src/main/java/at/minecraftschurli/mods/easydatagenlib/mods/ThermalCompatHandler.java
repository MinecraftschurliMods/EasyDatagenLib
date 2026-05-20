package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import at.minecraftschurli.mods.easydatagenlib.util.thermal.IngredientWithCount;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;
import static at.minecraftschurli.mods.easydatagenlib.util.Helpers.*;

public abstract class ThermalCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected ThermalCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("thermal", folder), namespace, output, registries);
    }
    //TODO Fuels, Fisher Boost, Hive Extractor, Potion Diffuser Boost, Rock Gen Mapping, Tree Extractor Boost, Tree Extractor Mapping

    public static class Bottling extends Processing {
        public Bottling(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("bottler", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Bottling(namespace, output, registries);
            }
        }
    }

    public static class Brewing extends Processing {
        public Brewing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("brewer", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Brewing(namespace, output, registries);
            }
        }
    }

    public static class Centrifuging extends Processing {
        public Centrifuging(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("centrifuge", namespace, output, registries);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower))
                .setEnergy(1600)
                .addInput(ingredient)
                .addOutputItem(output1, 3).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower))
                .setEnergy(3200)
                .addInput(ingredient)
                .addOutputItem(output1, 6).build();
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Centrifuging(namespace, output, registries);
            }
        }
    }

    public static class Chilling extends Processing {
        public Chilling(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("chiller", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Chilling(namespace, output, registries);
            }
        }
    }

    public static class Crucible extends Processing {
        public Crucible(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crucible", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crucible(namespace, output, registries);
            }
        }
    }

    public static class Crystallizing extends Processing {
        public Crystallizing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crystallizer", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crystallizing(namespace, output, registries);
            }
        }
    }

    public static class Furnace extends Processing {
        public Furnace(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("furnace", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Furnace(namespace, output, registries);
            }
        }
    }

    public static class Insolating extends Processing {
        public Insolating(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("insolator", namespace, output, registries);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower))
                .addInput(ingredient)
                .addOutputItem(flower, 1, 2).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower))
                .addInput(ingredient)
                .addOutputItem(flower, 1, 2).build();
        }

        @Override
        public void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {
            builder(toName(mushroom))
                .setWaterModifier(1.5f)
                .setEnergyModifier(0.5f)
                .addInput(ingredient(mushroom))
                .addOutputItem(mushroom, 1, 2).build();
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            if (sapling != null) {
                builder(toName(sapling))
                    .setWaterModifier(3)
                    .setEnergyModifier(3)
                    .addInput(ingredient(sapling))
                    .addOutputItem(log, 1, 6)
                    .addOutputItem(sapling, 1, 1.1f).build();
            }
        }

        /// @param id         The recipe id to use.
        /// @param experience The amount of experience this recipe awards.
        public Builder builder(String id, float experience) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), experience);
        }

        /// @param id The recipe id to use.
        public Builder builder(String id) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id));
        }

        public static class Builder extends Processing.Builder {
            private @Nullable Float waterModifier;

            protected Builder(Insolating provider, Identifier id, float experience) {
                super(provider, id, experience);
            }

            protected Builder(Insolating provider, Identifier id) {
                super(provider, id);
            }

            /// Sets the water modifier to use.
            ///
            /// @param waterModifier The water modifier to use.
            public Builder setWaterModifier(float waterModifier) {
                this.waterModifier = waterModifier;
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                super.toJson(json, registries);
                if (waterModifier != null) {
                    json.addProperty("water_mod", waterModifier);
                }
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Insolating(namespace, output, registries);
            }
        }
    }

    public static class Pressing extends Processing {
        public Pressing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("press", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (block != null) {
                builder(toName(gem) + "_packing")
                    .setEnergy(400)
                    .addInput(ingredient(gem), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(block)
                    .build();
                builder(toName(gem) + "_unpacking")
                    .setEnergy(400)
                    .addInput(ingredient(block))
                    .addInput(PRESS_UNPACKING_DIE)
                    .addOutputItem(gem, 9)
                    .build();
            }
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (ingotBlock != null) {
                builder(toName(ingot) + "_packing")
                    .setEnergy(400)
                    .addInput(ingredient(ingotTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingotBlock).build();
                if (ingotBlockTag != null) {
                    builder(toName(ingot) + "_unpacking")
                        .setEnergy(400)
                        .addInput(ingredient(ingotBlockTag))
                        .addInput(PRESS_UNPACKING_DIE)
                        .addOutputItem(ingot, 9).build();
                }
            }
            if (rawOreBlock != null) {
                builder(toName(rawOre) + "_packing")
                    .setEnergy(400)
                    .addInput(ingredient(rawOreTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(rawOreBlock).build();
                if (rawOreBlockTag != null) {
                    builder(toName(rawOre) + "_unpacking")
                        .setEnergy(400)
                        .addInput(ingredient(rawOreBlockTag))
                        .addInput(PRESS_UNPACKING_DIE)
                        .addOutputItem(rawOre, 9).build();
                }
            }
            if (nugget != null && nuggetTag != null) {
                builder(toName(nugget) + "_packing")
                    .setEnergy(400)
                    .addInput(ingredient(nuggetTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingot).build();
                builder(toName(nugget) + "_unpacking")
                    .setEnergy(400)
                    .addInput(ingredient(ingotTag))
                    .addInput(PRESS_UNPACKING_DIE)
                    .addOutputItem(nugget, 9).build();
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Pressing(namespace, output, registries);
            }
        }
    }

    public static class PulverizerRecycling extends Processing {
        public PulverizerRecycling(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("pulverizer_recycle", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new PulverizerRecycling(namespace, output, registries);
            }
        }
    }

    public static class Pulverizing extends Processing {
        public Pulverizing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("pulverizer", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (dust != null) {
                builder(toName(gem))
                    .setEnergyModifier(0.5f)
                    .addInput(ingredient(gem))
                    .addOutputItem(dust)
                    .build();
                builder(toName(oreTag), 0.2f)
                    .addInput(ingredient(oreTag))
                    .addOutputItem(dust, 2.5f)
                    .addOutputItem(GRAVEL, 0.2f)
                    .build();
            }
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (dust != null) {
                builder(toName(ingot))
                    .setEnergyModifier(0.5f)
                    .addInput(ingredient(ingot))
                    .addOutputItem(dust).build();
                if (secondaryDust == null) {
                    builder(toName(oreTag), 0.2f)
                        .addInput(ingredient(oreTag))
                        .addOutputItem(dust, 2.5f)
                        .addOutputItem(GRAVEL, 0.2f).build();
                } else {
                    builder(toName(oreTag), 0.2f)
                        .addInput(ingredient(oreTag))
                        .addOutputItem(dust, 2)
                        .addOutputItem(secondaryDust, 0.1f)
                        .addOutputItem(GRAVEL, 0.2f).build();
                }
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Pulverizing(namespace, output, registries);
            }
        }
    }

    public static class Pyrolyzing extends Processing {
        public Pyrolyzing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("pyrolyzer", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Pyrolyzing(namespace, output, registries);
            }
        }
    }

    public static class Refining extends Processing {
        public Refining(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("refinery", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Refining(namespace, output, registries);
            }
        }
    }

    public static class Sawing extends Processing {
        public Sawing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("sawmill", namespace, output, registries);
        }

        @Override
        public void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
            Item planks = family.getBaseBlock().asItem();
            if (logs != null) {
                builder(toName(logs))
                    .setEnergy(1000)
                    .addInput(ingredient(logs))
                    .addOutputItem(planks, 6)
                    .addOutputItem(THERMAL_SAWDUST, 1.25f).build();
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Sawing(namespace, output, registries);
            }
        }
    }

    public static class SmelterRecycling extends Processing {
        public SmelterRecycling(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("smelter_recycle", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new SmelterRecycling(namespace, output, registries);
            }
        }
    }

    public static class Smelting extends Processing {
        public Smelting(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("smelter", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            builder(toName(oreTag), 0.2f)
                .addInput(ingredient(oreTag))
                .addOutputItem(gem, 1.5f)
                .addOutputItem(RICH_SLAG, 0.15f)
                .build();
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (dust != null) {
                builder(toName(dust))
                    .setEnergyModifier(0.5f)
                    .addInput(ingredient(dust))
                    .addOutputItem(ingot).build();
            }
            if (secondaryIngot == null) {
                builder(toName(oreTag), 0.2f)
                    .addInput(ingredient(oreTag))
                    .addOutputItem(ingot, 1.5f)
                    .addOutputItem(RICH_SLAG, 0.15f).build();
            } else {
                builder(toName(oreTag), 0.2f)
                    .addInput(ingredient(oreTag))
                    .addOutputItem(ingot, 1)
                    .addOutputItem(secondaryIngot, 0.2f)
                    .addOutputItem(RICH_SLAG, 0.2f).build();
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Smelting(namespace, output, registries);
            }
        }
    }

    /// Note: Not all recipes can actually handle all information. However, Thermal's recipe specification allows everything to be specified for all recipes.
    /// {@see https://github.com/CoFH/ThermalCore/blob/1.19.x/src/main/java/cofh/thermal/lib/util/recipes/MachineRecipeSerializer.java}
    public static abstract class Processing extends ThermalCompatHandler<Processing.Builder> {
        protected Processing(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id         The recipe id to use.
        /// @param experience The amount of experience this recipe awards.
        public Builder builder(String id, float experience) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), experience);
        }

        /// @param id The recipe id to use.
        public Builder builder(String id) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id));
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> inputItems = new ArrayList<>();
            private final List<SizedFluidIngredient> inputFluids = new ArrayList<>();
            private final List<PotentiallyAbsentItemStack> outputItems = new ArrayList<>();
            private final List<PotentiallyAbsentFluidStack> outputFluids = new ArrayList<>();
            private final float experience;
            private @Nullable Integer energy;
            private @Nullable Float energyModifier;

            protected Builder(Processing provider, Identifier id, float experience) {
                super(id, provider);
                this.experience = experience;
            }

            protected Builder(Processing provider, Identifier id) {
                this(provider, id, 0);
            }

            /// Sets the energy of this recipe.
            ///
            /// @param energy The amount of energy to use.
            public Builder setEnergy(int energy) {
                this.energy = energy;
                return this;
            }

            /// Sets the energy modifier of this recipe.
            ///
            /// @param energyModifier The energy modifier to use.
            public Builder setEnergyModifier(float energyModifier) {
                this.energyModifier = energyModifier;
                return this;
            }

            /// Adds an input ingredient to this recipe.
            ///
            /// @param input The input ingredient to add.
            public Builder addInput(Ingredient input, int count) {
                inputItems.add(new IngredientWithCount(input, count));
                return this;
            }

            /// Adds an input ingredient to this recipe.
            ///
            /// @param input The input ingredient to add.
            public Builder addInput(Ingredient input) {
                return addInput(input, 1);
            }

            /// Adds an input ingredient to this recipe.
            ///
            /// @param input The input ingredient to add.
            public Builder addInput(SizedFluidIngredient input) {
                inputFluids.add(input);
                return this;
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Identifier output, int count, DataComponentPatch patch, float chance) {
                outputItems.add(new PotentiallyAbsentItemStack.WithChance(output, count, patch, chance));
                return this;
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            /// @param count  The output count to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Identifier output, int count, float chance) {
                return addOutputItem(output, count, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Identifier output, float chance) {
                return addOutputItem(output, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Item output, int count, DataComponentPatch patch, float chance) {
                return addOutputItem(itemId(output), count, patch, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            /// @param count  The output count to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Item output, int count, float chance) {
                return addOutputItem(output, count, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutputItem(Item output, float chance) {
                return addOutputItem(output, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            public Builder addOutputItem(Identifier output, int count, DataComponentPatch patch) {
                return addOutputItem(output, count, patch, 1f);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            /// @param count  The output count to use.
            public Builder addOutputItem(Identifier output, int count) {
                return addOutputItem(output, count, 1f);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The id of the output item to use.
            public Builder addOutputItem(Identifier output) {
                return addOutputItem(output, 1f);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            public Builder addOutputItem(Item output, int count, DataComponentPatch patch) {
                return addOutputItem(itemId(output), count, patch, 1f);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            /// @param count  The output count to use.
            public Builder addOutputItem(Item output, int count) {
                return addOutputItem(output, count, 1f);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param output The output item to use.
            public Builder addOutputItem(Item output) {
                return addOutputItem(output, 1f);
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The id of the output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch  The output components to use.
            public Builder addOutputFluid(Identifier output, int amount, DataComponentPatch patch) {
                outputFluids.add(new PotentiallyAbsentFluidStack(output, amount, patch));
                return this;
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The id of the output fluid to use.
            /// @param amount The output amount to use.
            public Builder addOutputFluid(Identifier output, int amount) {
                return addOutputFluid(output, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The id of the output fluid to use.
            public Builder addOutputFluid(Identifier output) {
                return addOutputFluid(output, 1);
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch  The output components to use.
            public Builder addOutputFluid(Fluid output, int amount, DataComponentPatch patch) {
                return addOutputFluid(fluidId(output), amount, patch);
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The output fluid to use.
            /// @param amount The output amount to use.
            public Builder addOutputFluid(Fluid output, int amount) {
                return addOutputFluid(output, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output fluid to this recipe.
            ///
            /// @param output The output fluid to use.
            public Builder addOutputFluid(Fluid output) {
                return addOutputFluid(output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                if (experience != 0) {
                    json.addProperty("experience", experience);
                }
                if (energy != null) {
                    json.addProperty("energy", energy);
                }
                if (energyModifier != null) {
                    json.addProperty("energy_mod", energyModifier);
                }
                JsonArray input = JsonUtil.toList(inputItems, registries);
                for (SizedFluidIngredient ingredient : inputFluids) {
                    input.add(SizedFluidIngredient.CODEC.encodeStart(registries.createSerializationContext(JsonOps.INSTANCE), ingredient).result().orElseThrow());
                }
                if (inputItems.size() == 1 && inputFluids.isEmpty()) {
                    json.add("ingredient", input.get(0));
                } else {
                    json.add("input", input);
                }
                json.add("output", JsonUtil.mergeArrays(JsonUtil.toList(outputItems, registries), JsonUtil.toList(outputFluids, registries)));
            }
        }
    }
}
