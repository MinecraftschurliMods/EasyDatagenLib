package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;
import static at.minecraftschurli.mods.easydatagenlib.util.Helpers.*;

public abstract class FarmersDelightCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected FarmersDelightCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("farmersdelight", folder), namespace, output, registries);
    }

    public static class Cooking extends FarmersDelightCompatHandler<Cooking.Builder> {
        public Cooking(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("cooking", namespace, output, registries);
        }

        /// @param id         The recipe id to use.
        /// @param duration   The duration to use.
        /// @param experience The amount of experience this recipe awards.
        /// @param output     The id of the output item to use.
        /// @param count      The output count to use.
        public Builder builder(String id, int duration, float experience, Identifier output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), duration, experience, output, count);
        }

        /// @param id         The recipe id to use.
        /// @param duration   The duration to use.
        /// @param experience The amount of experience this recipe awards.
        /// @param output     The id of the output item to use.
        public Builder builder(String id, int duration, float experience, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), duration, experience, output);
        }

        /// @param id         The recipe id to use.
        /// @param duration   The duration to use.
        /// @param experience The amount of experience this recipe awards.
        /// @param output     The output item to use.
        /// @param count      The output count to use.
        public Builder builder(String id, int duration, float experience, Item output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), duration, experience, output, count);
        }

        /// @param id         The recipe id to use.
        /// @param duration   The duration to use.
        /// @param experience The amount of experience this recipe awards.
        /// @param output     The output item to use.
        public Builder builder(String id, int duration, float experience, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), duration, experience, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> ingredients = new ArrayList<>();
            private final int duration;
            private final float experience;
            private final PotentiallyAbsentItemStack output;
            private @Nullable PotentiallyAbsentItemStack container = null;
            private @Nullable String recipeBookTab = null;

            protected Builder(Cooking provider, Identifier id, int duration, float experience, Identifier output, int count) {
                super(id, provider);
                this.duration = duration;
                this.experience = experience;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            protected Builder(Cooking provider, Identifier id, int duration, float experience, Identifier output) {
                this(provider, id, duration, experience, output, 1);
            }

            protected Builder(Cooking provider, Identifier id, int duration, float experience, Item output, int count) {
                this(provider, id, duration, experience, itemId(output), count);
            }

            protected Builder(Cooking provider, Identifier id, int duration, float experience, Item output) {
                this(provider, id, duration, experience, output, 1);
            }

            /// Sets the container item of this recipe.
            ///
            /// @param container The id of the container item to use.
            public Builder setContainer(Identifier container) {
                this.container = new PotentiallyAbsentItemStack(container);
                return this;
            }

            /// Sets the container item of this recipe.
            ///
            /// @param container The container item to use.
            public Builder setContainer(Item container) {
                return setContainer(itemId(container));
            }

            /// Sets the recipe book tab of this recipe.
            ///
            /// @param recipeBookTab The recipe book tab to use.
            public Builder setRecipeBookTab(String recipeBookTab) {
                this.recipeBookTab = recipeBookTab;
                return this;
            }

            /// Adds an input ingredient to this recipe.
            ///
            /// @param input The input ingredient to add.
            public Builder addInput(Ingredient input) {
                ingredients.add(input);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("cookingtime", duration);
                json.addProperty("experience", experience);
                json.add("result", output.toJson(registries));
                if (recipeBookTab != null) {
                    json.addProperty("recipe_book_tab", recipeBookTab);
                }
                if (container != null) {
                    json.add("container", container.toJson(registries));
                }
                json.add("ingredients", JsonUtil.toIngredientList(ingredients, registries));
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Cooking(namespace, output, registries);
            }
        }
    }

    public static class Cutting extends FarmersDelightCompatHandler<Cutting.Builder> {
        public Cutting(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("cutting", namespace, output, registries);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            Ingredient ingredient = ingredient(flower);
            builder(toName(flower), ingredient, ingredient(KNIVES))
                .addOutput(output1, 2).build();
        }

        @Override
        public void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
            Item planks = family.getBaseBlock().asItem();
            if (family.getVariants().containsKey(BlockFamily.Variant.DOOR)) {
                Item door = family.get(BlockFamily.Variant.DOOR).asItem();
                builder(toName(door), ingredient(door), ingredient(ItemTags.AXES))
                    .addOutput(planks).build();
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.TRAPDOOR)) {
                Item trapdoor = family.get(BlockFamily.Variant.TRAPDOOR).asItem();
                builder(toName(trapdoor), ingredient(trapdoor), ingredient(ItemTags.AXES))
                    .addOutput(planks).build();
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.SIGN)) {
                Item sign = family.get(BlockFamily.Variant.SIGN).asItem();
                builder(toName(sign), ingredient(sign), ingredient(ItemTags.AXES))
                    .addOutput(planks).build();
            }
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            /*builder(toName(log), ingredient(log), ingredient(AXE_STRIP))
                .setSound("minecraft:item.axe.strip")
                .addOutput(strippedLog)
                .addOutput(TREE_BARK).build();
            builder(toName(wood), ingredient(wood), ingredient(AXE_STRIP))
                .setSound("minecraft:item.axe.strip")
                .addOutput(strippedWood)
                .addOutput(TREE_BARK).build();*/
        }

        /// @param id    The recipe id to use.
        /// @param input The input ingredient to use.
        /// @param tool  The tool to use.
        public Builder builder(String id, Ingredient input, Ingredient tool) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, tool);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private final Ingredient tool;
            private @Nullable String sound;

            protected Builder(Cutting provider, Identifier id, Ingredient input, Ingredient tool) {
                super(id, provider);
                this.input = input;
                this.tool = tool;
            }

            /// Sets the sound of this recipe.
            ///
            /// @param sound The sound to use.
            public Builder setSound(String sound) {
                this.sound = sound;
                return this;
            }

            /// Adds an output to this recipe.
            ///
            /// @param item   The id of the output item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutput(Identifier item, int count, DataComponentPatch patch, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, count, patch, chance));
                return this;
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The id of the output item to use.
            /// @param count The output count to use.
            /// @param patch The output components to use.
            public Builder addOutput(Identifier item, int count, DataComponentPatch patch) {
                return addOutput(item, count, patch, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The id of the output item to use.
            /// @param count The output count to use.
            public Builder addOutput(Identifier item, int count) {
                return addOutput(item, count, DataComponentPatch.EMPTY);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item The id of the output item to use.
            public Builder addOutput(Identifier item) {
                return addOutput(item, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item   The item to use.
            /// @param count  The output count to use.
            /// @param patch  The output components to use.
            /// @param chance The chance that this output will be used.
            public Builder addOutput(Item item, int count, DataComponentPatch patch, float chance) {
                return addOutput(itemId(item), count, patch, chance);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The item to use.
            /// @param count The output count to use.
            /// @param patch The output components to use.
            public Builder addOutput(Item item, int count, DataComponentPatch patch) {
                return addOutput(item, count, patch, 1);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item  The item to use.
            /// @param count The output count to use.
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, DataComponentPatch.EMPTY);
            }

            /// Adds an output to this recipe.
            ///
            /// @param item The item to use.
            public Builder addOutput(Item item) {
                return addOutput(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("ingredients", JsonUtil.toIngredientList(List.of(input), registries));
                json.add("tool", JsonUtil.toJson(tool, registries));
                if (sound != null) {
                    json.addProperty("sound", sound);
                }
                json.add("result", JsonUtil.toList(outputs, registries));
            }
        }
        
        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Cutting(namespace, output, registries);
            }
        }
    }
}
