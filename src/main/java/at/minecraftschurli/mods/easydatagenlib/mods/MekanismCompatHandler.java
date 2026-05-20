package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonSerializable;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import at.minecraftschurli.mods.easydatagenlib.util.mekanism.Chemical;
import at.minecraftschurli.mods.easydatagenlib.util.mekanism.Gas;
import at.minecraftschurli.mods.easydatagenlib.util.mekanism.InfuseType;
import at.minecraftschurli.mods.easydatagenlib.util.mekanism.IngredientWithAmount;
import at.minecraftschurli.mods.easydatagenlib.util.mekanism.Pigment;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;
import static at.minecraftschurli.mods.easydatagenlib.util.Helpers.*;

public abstract class MekanismCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected MekanismCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("mekanism", folder), namespace, output, registries);
    }
    //TODO Chemical Infusing, Compressing, Crystallizing, Dissolution, Energy Conversion, Evaporating, Injecting, Metallurgic Infusing, Nucleosynthesizing, Painting, Pigment Mixing, Purifying, Reaction, Rotary, Separating, Washing

    public static class Activating extends GasToGasRecipe {
        public Activating(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("activating", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Activating(namespace, output, registries);
            }
        }
    }

    public static class Centrifuging extends GasToGasRecipe {
        public Centrifuging(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("centrifuging", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Centrifuging(namespace, output, registries);
            }
        }
    }

    public static class Combining extends IngredientToItemRecipe {
        public Combining(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("combining", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (dust != null) {
                builder(toName(gem) + "_to_deepslate_ore", ingredient(dust), 5, ingredient(Tags.Items.COBBLESTONES_DEEPSLATE), deepslateOre).build();
                builder(toName(gem) + "_to_ore", ingredient(dust), 5, ingredient(Tags.Items.COBBLESTONES_NORMAL), ore).build();
            }
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            builder(toName(deepslateOre) + "_from_raw", ingredient(rawOre), 8, ingredient(Tags.Items.COBBLESTONES_DEEPSLATE), deepslateOre).build();
            builder(toName(ore) + "_from_raw", ingredient(rawOre), 8, ingredient(Tags.Items.COBBLESTONES_NORMAL), ore).build();
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param extraCount The extra input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param extraCount The extra input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, extraCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param extraCount The extra input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param extraCount  The extra input ingredient count to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param extraCount The extra input ingredient count to use.
        /// @param output     The output item to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, extraCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, extraInput, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param extraInput  The extra input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param extraInput The extra input ingredient to use.
        /// @param output     The output item to use.
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, extraInput, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private final IngredientWithAmount extraInput;

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output, int outputCount, DataComponentPatch patch) {
                super(provider, id, input, inputCount, output, outputCount, patch);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output, int outputCount) {
                super(provider, id, input, inputCount, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Identifier output) {
                super(provider, id, input, inputCount, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount, DataComponentPatch patch) {
                super(provider, id, input, inputCount, output, outputCount, patch);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount) {
                super(provider, id, input, inputCount, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output) {
                super(provider, id, input, inputCount, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output, int outputCount, DataComponentPatch patch) {
                super(provider, id, input, output, outputCount, patch);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output, int outputCount) {
                super(provider, id, input, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Identifier output) {
                super(provider, id, input, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount, DataComponentPatch patch) {
                super(provider, id, input, output, outputCount, patch);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount) {
                super(provider, id, input, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, int extraCount, Item output) {
                super(provider, id, input, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount, patch);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output, int outputCount) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Identifier output) {
                this(provider, id, input, inputCount, extraInput, 1, output);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount, patch);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, int inputCount, Ingredient extraInput, Item output) {
                this(provider, id, input, inputCount, extraInput, 1, output);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Identifier output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, extraInput, 1, output, outputCount, patch);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Identifier output, int outputCount) {
                this(provider, id, input, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Identifier output) {
                this(provider, id, input, extraInput, 1, output);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, extraInput, 1, output, outputCount, patch);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Item output, int outputCount) {
                this(provider, id, input, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, Identifier id, Ingredient input, Ingredient extraInput, Item output) {
                this(provider, id, input, extraInput, 1, output);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("mainInput", input.toJson(registries));
                json.add("extraInput", extraInput.toJson(registries));
                json.add("output", output.toJson(registries));
            }
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Combining(namespace, output, registries);
            }
        }
    }

    public static class Crushing extends IngredientToItemRecipe {
        public Crushing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crushing", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (dust != null) {
                builder(toName(gem) + "_to_dust", ingredient(gem), dust).build();
            }
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (dust != null) {
                builder(toName(dust) + "_from_ingot", ingredient(ingot), dust).build();
            }
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, BIO_FUEL, 5).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, BIO_FUEL, 5).build();
        }

        @Override
        public void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {
            builder(toName(mushroom), ingredient(mushroom), BIO_FUEL, 5).build();
            if (mushroomBlock != null) {
                builder(toName(mushroomBlock), ingredient(mushroomBlock), BIO_FUEL, 7).build();
            }
        }

        @Override
        public void addFungusAndRootsProcessing(Item fungus, Item roots, Identifier nylium, Identifier stem, Identifier wartBlock) {
            builder(toName(fungus), ingredient(fungus), BIO_FUEL, 5).build();
            builder(toName(roots), ingredient(roots), BIO_FUEL, 5).build();
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            if (leaves != null) {
                builder(toName(leaves), ingredient(leaves), BIO_FUEL, 2).build();
            }
            if (sapling != null) {
                builder(toName(sapling), ingredient(sapling), BIO_FUEL, 2).build();
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crushing(namespace, output, registries);
            }
        }
    }

    public static class Enriching extends IngredientToItemRecipe {
        public Enriching(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("enriching", namespace, output, registries);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (dust != null) {
                builder(toName(gem) + "_from_dust", ingredient(dust), gem).build();
            }
            builder(toName(gem) + "_from_ore", ingredient(oreTag), gem, 2).build();
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (dust != null) {
                builder(toName(dust) + "_from_ore", ingredient(oreTag), dust).build();
                builder(toName(dust) + "_from_raw", ingredient(rawOre), 3, dust, 4).build();
                if (rawOreBlockTag != null) {
                    builder(toName(dust) + "_from_raw_block", ingredient(rawOreBlockTag), dust, 12).build();
                }
            }
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, output1, 2).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, output1, 4).build();
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Enriching(namespace, output, registries);
            }
        }
    }

    public static class GasConversion extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public GasConversion(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("gas_conversion", namespace, output, registries);
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new GasConversion(namespace, output, registries);
            }
        }
    }

    public static class InfusionConversion extends IngredientToTRecipe<Chemical.Stack<InfuseType>> {
        public InfusionConversion(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("infusion_conversion", namespace, output, registries);
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new InfusionConversion(namespace, output, registries);
            }
        }
    }

    public static class Oxidizing extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public Oxidizing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("oxidizing", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Oxidizing(namespace, output, registries);
            }
        }
    }

    public static class PigmentExtracting extends IngredientToTRecipe<Chemical.Stack<Pigment>> {
        public PigmentExtracting(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("pigment_extracting", namespace, output, registries);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            if (DYES.containsKey(output1)) {
                builder(toName(flower), ingredient, new Chemical.Stack<>(Pigment.byDyeColor(DYES.get(output1)), 768)).build();
            }
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            if (DYES.containsKey(output1)) {
                builder(toName(flower), ingredient, new Chemical.Stack<>(Pigment.byDyeColor(DYES.get(output1)), 1536)).build();
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new PigmentExtracting(namespace, output, registries);
            }
        }
    }

    public static class Sawing extends IngredientToItemRecipe {
        public Sawing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("sawing", namespace, output, registries);
        }

        @Override
        public void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
            Item planks = family.getBaseBlock().asItem();
            if (family.getVariants().containsKey(BlockFamily.Variant.DOOR)) {
                Item door = family.get(BlockFamily.Variant.DOOR).asItem();
                builder(toName(door), ingredient(door), planks, 2).build();
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.FENCE_GATE)) {
                Item fenceGate = family.get(BlockFamily.Variant.FENCE_GATE).asItem();
                builder(toName(fenceGate), ingredient(fenceGate), planks, 2)
                    .setSecondaryOutput(STICK, 4).build();
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.TRAPDOOR)) {
                Item trapdoor = family.get(BlockFamily.Variant.TRAPDOOR).asItem();
                builder(toName(trapdoor), ingredient(trapdoor), planks, 3).build();
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.PRESSURE_PLATE)) {
                Item pressurePlate = family.get(BlockFamily.Variant.PRESSURE_PLATE).asItem();
                builder(toName(pressurePlate), ingredient(pressurePlate), planks, 2).build();
            }
            if (boat != null) {
                builder(toName(boat), ingredient(boat), planks, 5).build();
                if (chestBoat != null) {
                    builder(toName(chestBoat), ingredient(chestBoat), boat)
                        .setSecondaryOutput(CHEST).build();
                }
            }
            if (logs != null) {
                builder(toName(logs), ingredient(logs), planks, 6)
                    .setSecondaryOutput(MEKANISM_SAWDUST, 0.25f).build();
            }
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param output     The output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The id of the output item to use.
        public Builder builder(String id, Ingredient input, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output item to use.
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private @Nullable PotentiallyAbsentItemStack secondaryOutput = null;
            private float chance = 1;

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Identifier output, int outputCount, DataComponentPatch patch) {
                super(provider, id, input, inputCount, output, outputCount, patch);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Identifier output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Identifier output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, inputCount, itemId(output), outputCount, patch);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Item output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, int inputCount, Item output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Identifier output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, 1, output, outputCount, patch);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Identifier output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Identifier output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, itemId(output), outputCount, patch);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }

            /// Sets the secondary output of this recipe.
            ///
            /// @param output The id of the output item to use.
            public Builder setSecondaryOutput(Identifier output) {
                return setSecondaryOutput(output, 1);
            }

            /// Sets the secondary output of this recipe.
            ///
            /// @param output The output item to use.
            public Builder setSecondaryOutput(Item output) {
                return setSecondaryOutput(output, 1);
            }

            /// Sets the secondary output of this recipe.
            ///
            /// @param output      The id of the output item to use.
            /// @param outputCount The output count to use.
            /// @param patch       The output components to use.
            /// @param chance      The chance that this output will be used.
            public Builder setSecondaryOutput(Identifier output, int outputCount, DataComponentPatch patch, float chance) {
                this.secondaryOutput = new PotentiallyAbsentItemStack(output, outputCount, patch);
                this.chance = chance;
                return this;
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The id of the output item to use.
            /// @param outputCount The output count to use.
            /// @param chance      The chance that this output will be used.
            public Builder setSecondaryOutput(Identifier output, int outputCount, float chance) {
                return setSecondaryOutput(output, outputCount, DataComponentPatch.EMPTY, chance);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output The id of the output item to use.
            /// @param chance The chance that this output will be used.
            public Builder setSecondaryOutput(Identifier output, float chance) {
                return setSecondaryOutput(output, 1, chance);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The id of the output item to use.
            /// @param outputCount The output count to use.
            /// @param patch       The output components to use.
            public Builder setSecondaryOutput(Identifier output, int outputCount, DataComponentPatch patch) {
                return setSecondaryOutput(output, outputCount, patch, 1);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The id of the output item to use.
            /// @param outputCount The output count to use.
            public Builder setSecondaryOutput(Identifier output, int outputCount) {
                return setSecondaryOutput(output, outputCount, DataComponentPatch.EMPTY);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The output item to use.
            /// @param outputCount The output count to use.
            /// @param patch       The output components to use.
            /// @param chance      The chance that this output will be used.
            public Builder setSecondaryOutput(Item output, int outputCount, DataComponentPatch patch, float chance) {
                return setSecondaryOutput(itemId(output), outputCount, patch, chance);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The output item to use.
            /// @param outputCount The output count to use.
            /// @param chance      The chance that this output will be used.
            public Builder setSecondaryOutput(Item output, int outputCount, float chance) {
                return setSecondaryOutput(output, outputCount, DataComponentPatch.EMPTY, chance);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output The output item to use.
            /// @param chance The chance that this output will be used.
            public Builder setSecondaryOutput(Item output, float chance) {
                return setSecondaryOutput(output, 1, chance);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The output item to use.
            /// @param outputCount The output count to use.
            /// @param patch       The output components to use.
            public Builder setSecondaryOutput(Item output, int outputCount, DataComponentPatch patch) {
                return setSecondaryOutput(itemId(output), outputCount, patch);
            }

            /// Sets the secondary output to use.
            ///
            /// @param output      The output item to use.
            /// @param outputCount The output count to use.
            public Builder setSecondaryOutput(Item output, int outputCount) {
                return setSecondaryOutput(output, outputCount, DataComponentPatch.EMPTY);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", input.toJson(registries));
                json.add("mainOutput", output.toJson(registries));
                if (secondaryOutput != null && chance > 0 && chance <= 1) {
                    json.add("secondaryOutput", secondaryOutput.toJson(registries));
                    json.addProperty("secondaryChance", chance);
                }
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Sawing(namespace, output, registries);
            }
        }
    }

    public static class Smelting extends IngredientToItemRecipe {
        public Smelting(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("smelting", namespace, output, registries);
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Smelting(namespace, output, registries);
            }
        }
    }

    public static abstract class Abstract1To1Recipe<I extends JsonSerializable, O extends JsonSerializable, B extends Abstract1To1Recipe.Builder<I, O>> extends MekanismCompatHandler<B> {
        protected Abstract1To1Recipe(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        public static class Builder<I extends JsonSerializable, O extends JsonSerializable> extends AbstractRecipeBuilder<Builder<I, O>> {
            protected final I input;
            protected final O output;

            protected Builder(Abstract1To1Recipe<I, O, Builder<I, O>> provider, Identifier id, I input, O output) {
                super(id, provider);
                this.input = input;
                this.output = output;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", input.toJson(registries));
                json.add("output", output.toJson(registries));
            }
        }
    }

    public static abstract class IngredientToTRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<IngredientWithAmount, T, IngredientToTRecipe.Builder<T>> {
        protected IngredientToTRecipe(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param outputCount The input ingredient count to use.
        /// @param output      The output chemical to use.
        public Builder<T> builder(String id, Ingredient input, int outputCount, T output) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, outputCount, output);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output chemical to use.
        public Builder<T> builder(String id, Ingredient input, T output) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<IngredientWithAmount, T> {
            protected Builder(IngredientToTRecipe<T> provider, Identifier id, Ingredient input, int outputCount, T output) {
                super((Abstract1To1Recipe<IngredientWithAmount, T, Abstract1To1Recipe.Builder<IngredientWithAmount,T>>) (Object) provider, id, new IngredientWithAmount(input, outputCount), output);
            }

            protected Builder(IngredientToTRecipe<T> provider, Identifier id, Ingredient input, T output) {
                this(provider, id, input, 1, output);
            }
        }
    }

    public static abstract class TToItemRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<T, PotentiallyAbsentItemStack, TToItemRecipe.Builder<T>> {
        protected TToItemRecipe(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder<T> builder(String id, T input, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder<T> builder(String id, T input, Identifier output, int outputCount) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The id of the output item to use.
        public Builder<T> builder(String id, T input, Identifier output) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder<T> builder(String id, T input, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder<T> builder(String id, T input, Item output, int outputCount) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output item to use.
        public Builder<T> builder(String id, T input, Item output) {
            return new Builder<>(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<T, PotentiallyAbsentItemStack> {
            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Identifier output, int outputCount, DataComponentPatch patch) {
                super((Abstract1To1Recipe<T, PotentiallyAbsentItemStack, Abstract1To1Recipe.Builder<T, PotentiallyAbsentItemStack>>) (Object) provider, id, input, new PotentiallyAbsentItemStack(output, outputCount, patch));
            }

            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Identifier output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Identifier output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, itemId(output), outputCount, patch);
            }

            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(TToItemRecipe<T> provider, Identifier id, T input, Item output) {
                this(provider, id, input, output, 1);
            }
        }
    }

    public static abstract class IngredientToItemRecipe extends Abstract1To1Recipe<IngredientWithAmount, PotentiallyAbsentItemStack, IngredientToItemRecipe.Builder> {
        protected IngredientToItemRecipe(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param inputCount  The input ingredient count to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output, outputCount);
        }

        /// @param id         The recipe id to use.
        /// @param input      The input ingredient to use.
        /// @param inputCount The input ingredient count to use.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, Ingredient input, int inputCount, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, inputCount, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Identifier output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The id of the output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Identifier output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The id of the output item to use.
        public Builder builder(String id, Ingredient input, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        /// @param patch       The output components to use.
        public Builder builder(String id, Ingredient input, Item output, int outputCount, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount, patch);
        }

        /// @param id          The recipe id to use.
        /// @param input       The input ingredient to use.
        /// @param output      The output item to use.
        /// @param outputCount The output count to use.
        public Builder builder(String id, Ingredient input, Item output, int outputCount) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, outputCount);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output item to use.
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<IngredientWithAmount, PotentiallyAbsentItemStack> {
            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Identifier output, int outputCount, DataComponentPatch patch) {
                super((Abstract1To1Recipe<IngredientWithAmount, PotentiallyAbsentItemStack, Abstract1To1Recipe.Builder<IngredientWithAmount, PotentiallyAbsentItemStack>>) (Object) provider, id, new IngredientWithAmount(input, inputCount), new PotentiallyAbsentItemStack(output, outputCount, patch));
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Identifier output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Identifier output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, inputCount, itemId(output), outputCount, patch);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Item output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, int inputCount, Item output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Identifier output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, 1, output, outputCount, patch);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Identifier output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Identifier output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Item output, int outputCount, DataComponentPatch patch) {
                this(provider, id, input, itemId(output), outputCount, patch);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, DataComponentPatch.EMPTY);
            }

            protected Builder(IngredientToItemRecipe provider, Identifier id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }
        }
    }

    public static abstract class GasToGasRecipe extends Abstract1To1Recipe<Chemical.Stack<Gas>, Chemical.Stack<Gas>, GasToGasRecipe.Builder> {
        protected GasToGasRecipe(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input gas stack to use.
        /// @param output The output gas stack to use.
        public Builder builder(String id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<Chemical.Stack<Gas>, Chemical.Stack<Gas>> {
            protected Builder(GasToGasRecipe provider, Identifier id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
                super((Abstract1To1Recipe<Chemical.Stack<Gas>, Chemical.Stack<Gas>, Abstract1To1Recipe.Builder<Chemical.Stack<Gas>, Chemical.Stack<Gas>>>) (Object) provider, id, input, output);
            }
        }
    }
}
