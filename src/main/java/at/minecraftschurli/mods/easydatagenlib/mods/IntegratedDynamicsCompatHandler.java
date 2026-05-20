package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;

public abstract class IntegratedDynamicsCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected IntegratedDynamicsCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("integrateddynamics", folder), namespace, output, registries);
    }
    //TODO Drying Basin, Mechanical Drying Basin

    public static class MechanicalSqueezing extends IntegratedDynamicsCompatHandler<MechanicalSqueezing.Builder> {
        public MechanicalSqueezing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("mechanical_squeezer", namespace, output, registries);
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            builder(toName(oreTag), ingredient(oreTag), 40)
                .addItem(rawOre, 3)
                .addItem(rawOre, 0.5f)
                .addItem(rawOre, 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition<>(oreTag))).build();
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, 5)
                .addItem(output1, 4)
                .addItem(output1, 2, 0.5f).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, 5)
                .addItem(output1, 8)
                .addItem(output1, 2, 0.5f)
                .addItem(output1, 2, 0.5f).build();
        }

        /// @param id       The recipe id to use.
        /// @param input    The input ingredient to use.
        /// @param duration The duration to use.
        public Builder builder(String id, Ingredient input, int duration) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, duration);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private final int duration;
            private @Nullable PotentiallyAbsentFluidStack outputFluid = null;

            protected Builder(MechanicalSqueezing provider, Identifier id, Ingredient input, int duration) {
                super(id, provider);
                this.input = input;
                this.duration = duration;
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The id of the output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder setOutputFluid(Identifier fluid, int amount, DataComponentPatch patch) {
                this.outputFluid = new PotentiallyAbsentFluidStack(fluid, amount, patch);
                return this;
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The id of the output fluid to use.
            /// @param amount The output amount to use.
            public Builder setOutputFluid(Identifier fluid, int amount) {
                return setOutputFluid(fluid, amount, DataComponentPatch.EMPTY);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid The id of the output fluid to use.
            public Builder setOutputFluid(Identifier fluid) {
                return setOutputFluid(fluid, 1);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder setOutputFluid(Fluid fluid, int amount, DataComponentPatch patch) {
                return setOutputFluid(fluidId(fluid), amount, patch);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The output fluid to use.
            /// @param amount The output amount to use.
            public Builder setOutputFluid(Fluid fluid, int amount) {
                return setOutputFluid(fluid, amount, DataComponentPatch.EMPTY);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid The output fluid to use.
            public Builder setOutputFluid(Fluid fluid) {
                return setOutputFluid(fluid, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, int amount, DataComponentPatch patch, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, amount, patch, chance));
                return this;
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, int amount, float chance) {
                return addItem(item, amount, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, float chance) {
                return addItem(item, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, int amount, DataComponentPatch patch, float chance) {
                return addItem(itemId(item), amount, patch, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder addItem(Identifier item, int amount, DataComponentPatch patch) {
                return addItem(item, amount, patch, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            public Builder addItem(Identifier item, int amount) {
                return addItem(item, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item The id of the output item to use.
            public Builder addItem(Identifier item) {
                return addItem(item, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder addItem(Item item, int amount, DataComponentPatch patch) {
                return addItem(itemId(item), amount, patch);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item The output item to use.
            public Builder addItem(Item item) {
                return addItem(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("item", JsonUtil.toJson(input, registries));
                json.addProperty("duration", duration);
                JsonObject output = new JsonObject();
                output.add("items", JsonUtil.toList(outputs, registries));
                if (outputFluid != null) {
                    output.add("fluid", outputFluid.toJson(registries));
                }
                json.add("result", output);
            }
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new MechanicalSqueezing(namespace, output, registries);
            }
        }
    }

    public static class Squeezing extends IntegratedDynamicsCompatHandler<Squeezing.Builder> {
        public Squeezing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("squeezer", namespace, output, registries);
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            builder(toName(oreTag), ingredient(oreTag))
                .addItem(rawOre, 3)
                .addItem(rawOre, 0.5f)
                .addItem(rawOre, 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition<>(oreTag))).build();
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient)
                .addItem(output1, 4).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient)
                .addItem(output1, 8).build();
        }

        /// @param id    The recipe id to use.
        /// @param input The input ingredient to use.
        public Builder builder(String id, Ingredient input) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private @Nullable PotentiallyAbsentFluidStack outputFluid = null;

            protected Builder(Squeezing provider, Identifier id, Ingredient input) {
                super(id, provider);
                this.input = input;
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The id of the output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder setOutputFluid(Identifier fluid, int amount, DataComponentPatch patch) {
                this.outputFluid = new PotentiallyAbsentFluidStack(fluid, amount, patch);
                return this;
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The id of the output fluid to use.
            /// @param amount The output amount to use.
            public Builder setOutputFluid(Identifier fluid, int amount) {
                return setOutputFluid(fluid, amount, DataComponentPatch.EMPTY);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid The id of the output fluid to use.
            public Builder setOutputFluid(Identifier fluid) {
                return setOutputFluid(fluid, 1);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The output fluid to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder setOutputFluid(Fluid fluid, int amount, DataComponentPatch patch) {
                return setOutputFluid(fluidId(fluid), amount, patch);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid  The output fluid to use.
            /// @param amount The output amount to use.
            public Builder setOutputFluid(Fluid fluid, int amount) {
                return setOutputFluid(fluid, amount, DataComponentPatch.EMPTY);
            }

            /// Sets the output fluid of this recipe.
            ///
            /// @param fluid The output fluid to use.
            public Builder setOutputFluid(Fluid fluid) {
                return setOutputFluid(fluid, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, int amount, DataComponentPatch patch, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, amount, patch, chance));
                return this;
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, int amount, float chance) {
                return addItem(item, amount, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Identifier item, float chance) {
                return addItem(item, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, int amount, DataComponentPatch patch, float chance) {
                return addItem(itemId(item), amount, patch, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, DataComponentPatch.EMPTY, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param chance The chance that this output will be used.
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, chance);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder addItem(Identifier item, int amount, DataComponentPatch patch) {
                return addItem(item, amount, patch, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param amount The output amount to use.
            public Builder addItem(Identifier item, int amount) {
                return addItem(item, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item The id of the output item to use.
            public Builder addItem(Identifier item) {
                return addItem(item, 1);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            /// @param patch The output components to use.
            public Builder addItem(Item item, int amount, DataComponentPatch patch) {
                return addItem(itemId(item), amount, patch);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param amount The output amount to use.
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, DataComponentPatch.EMPTY);
            }

            /// Adds an output item to this recipe.
            ///
            /// @param item The output item to use.
            public Builder addItem(Item item) {
                return addItem(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("item", JsonUtil.toJson(input, registries));
                JsonObject output = new JsonObject();
                output.add("items", JsonUtil.toList(outputs, registries));
                if (outputFluid != null) {
                    output.add("fluid", outputFluid.toJson(registries));
                }
                json.add("result", output);
            }
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Squeezing(namespace, output, registries);
            }
        }
    }
}
