package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ArsNouveauCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected ArsNouveauCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("ars_nouveau", folder), namespace, output, registries);
    }

    public static class Crushing extends ArsNouveauCompatHandler<Crushing.Builder> {
        public Crushing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crush", namespace, output, registries);
        }

        /// @param id    The recipe id to use.
        /// @param input The input item to use.
        public Builder builder(String id, Ingredient input) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(ICompatHandler.toName(flower), ingredient).addOutput(output1, 2).build();
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(itemId(flower).getPath(), ingredient).addOutput(output1, 4).build();
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Pair<PotentiallyAbsentItemStack, Integer>> output = new ArrayList<>();
            private final Ingredient input;
            private boolean skipBlockPlace = false;

            protected Builder(Crushing provider, Identifier id, Ingredient input) {
                super(id, provider);
                this.input = input;
            }

            /// Sets this recipe's skipBlockPlace property to true.
            public Builder skipBlockPlace() {
                skipBlockPlace = true;
                return this;
            }

            /// Adds an output to this recipe.
            ///
            /// @param item     The output item to use.
            /// @param count    The output count to use.
            /// @param chance   The chance that this output will be used.
            /// @param maxRange The max range to use.
            public Builder addOutput(Item item, int count, float chance, int maxRange) {
                return addOutput(itemId(item), count, chance, maxRange);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param count  The output count to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutput(Item item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The output item to use.
            /// @param count The output count to use.
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item The output item to use.
            public Builder addOutput(Item item) {
                return addOutput(item, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item     The output item to use.
            /// @param count    The output count to use.
            /// @param chance   The chance that this output will be used.
            /// @param maxRange The max range to use.
            public Builder addOutput(Identifier item, int count, float chance, int maxRange) {
                output.add(Pair.of(new PotentiallyAbsentItemStack.WithChance(item, count, chance), maxRange)); // doesn't support NBT
                return this;
            }

            /// Adds an output to this recipe.
            ///
            /// @param item   The output item to use.
            /// @param count  The output count to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutput(Identifier item, int count, float chance) {
                return addOutput(item, count, chance, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The output item to use.
            /// @param count The output count to use.
            public Builder addOutput(Identifier item, int count) {
                return addOutput(item, count, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item The output item to use.
            public Builder addOutput(Identifier item) {
                return addOutput(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("output", JsonUtil.toList(output, e -> {
                    JsonObject o = e.getFirst().toJson(registries);
                    if (!o.has("chance")) {
                        o.addProperty("chance", 1f);
                    }
                    o.addProperty("maxRange", e.getSecond());
                    return o;
                }));
                json.addProperty("skip_block_place", skipBlockPlace);
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crushing(namespace, output, registries);
            }
        }
    }

    public static class Glyph extends ArsNouveauCompatHandler<Glyph.Builder> {
        public Glyph(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("glyph", namespace, output, registries);
        }

        /// @param id    The recipe id to use.
        /// @param item  The id of the output item to use.
        /// @param count The output count to use.
        public Builder builder(String id, Identifier item, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), item, count);
        }

        /// @param id   The recipe id to use.
        /// @param item The id of the output item to use.
        public Builder builder(String id, Identifier item) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), item);
        }

        /// @param id    The recipe id to use.
        /// @param item  The output item to use.
        /// @param count The output count to use.
        public Builder builder(String id, Item item, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), item, count);
        }

        /// @param id   The recipe id to use.
        /// @param item The output item to use.
        public Builder builder(String id, Item item) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), item);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> inputItems = new ArrayList<>();
            private final PotentiallyAbsentItemStack output;
            private int experience = 0;

            protected Builder(Glyph provider, Identifier id, Identifier item, int count) {
                super(id, provider);
                output = new PotentiallyAbsentItemStack(item, count); // doesn't support NBT
            }

            protected Builder(Glyph provider, Identifier id, Identifier item) {
                this(provider, id, item, 1);
            }

            protected Builder(Glyph provider, Identifier id, Item item, int count) {
                this(provider, id, itemId(item), count);
            }

            protected Builder(Glyph provider, Identifier id, Item item) {
                this(provider, id, item, 1);
            }

            /// Sets the amount of experience this recipe awards.
            ///
            /// @param experience The amount of experience this recipe awards.
            public Builder setExperience(int experience) {
                this.experience = experience;
                return this;
            }

            /// Adds an input ingredient to this recipe.
            ///
            /// @param input The input ingredient to add.
            public Builder addInput(Ingredient input) {
                inputItems.add(input);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                if (this.inputItems.isEmpty())
                    throw new IllegalStateException("Input item list is empty for recipe " + id);
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("exp", experience);
                json.add("inputItems", JsonUtil.toIngredientList(inputItems, registries));
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Glyph(namespace, output, registries);
            }
        }
    }

    public static class Imbueing extends ArsNouveauCompatHandler<Imbueing.Builder> {
        public Imbueing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("imbuement", namespace, output, registries);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input item to use.
        /// @param output The id of the output item to use.
        /// @param count  The output count to use.
        /// @param mana   The amount of mana to use.
        public Builder builder(String id, Ingredient input, Identifier output, int count, int mana) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count, mana);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input item to use.
        /// @param output The id of the output item to use.
        /// @param mana   The amount of mana to use.
        public Builder builder(String id, Ingredient input, Identifier output, int mana) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, mana);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input item to use.
        /// @param output The id of the output item to use.
        /// @param count  The output count to use.
        /// @param mana   The amount of mana to use.
        public Builder builder(String id, Ingredient input, Item output, int count, int mana) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count, mana);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input item to use.
        /// @param output The id of the output item to use.
        /// @param mana   The amount of mana to use.
        public Builder builder(String id, Ingredient input, Item output, int mana) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, mana);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> secondaryInputs = new ArrayList<>();
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int mana;

            protected Builder(Imbueing provider, Identifier id, Ingredient input, Identifier output, int count, int mana) {
                super(id, provider);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
                this.mana = mana;
            }

            protected Builder(Imbueing provider, Identifier id, Ingredient input, Identifier output, int mana) {
                this(provider, id, input, output, 1, mana);
            }

            protected Builder(Imbueing provider, Identifier id, Ingredient input, Item output, int count, int mana) {
                this(provider, id, input, itemId(output), count, mana);
            }

            protected Builder(Imbueing provider, Identifier id, Ingredient input, Item output, int mana) {
                this(provider, id, input, output, 1, mana);
            }

            /// Adds a secondary input to this recipe.
            ///
            /// @param secondaryInput The secondary input to add.
            public Builder addSecondaryIngredient(Ingredient secondaryInput) {
                secondaryInputs.add(secondaryInput);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.addProperty("output", output.item.toString());
                json.addProperty("count", output.count);
                json.addProperty("source", mana);
                json.add("pedestalItems", JsonUtil.toIngredientList(secondaryInputs, registries));
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Imbueing(namespace, output, registries);
            }
        }
    }
}
