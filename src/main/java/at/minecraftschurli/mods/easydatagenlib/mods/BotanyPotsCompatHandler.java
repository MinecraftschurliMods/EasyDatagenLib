package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import at.minecraftschurli.mods.easydatagenlib.util.botanypots.DisplayState;
import at.minecraftschurli.mods.easydatagenlib.util.botanypots.HarvestEntry;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;
import static at.minecraftschurli.mods.easydatagenlib.util.Helpers.SHROOMLIGHT;

public abstract class BotanyPotsCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected BotanyPotsCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("botanypots", folder), namespace, output, registries);
    }
    //TODO Fertilizer, Pot Interaction

    public static class Crop extends BotanyPotsCompatHandler<Crop.Builder> {
        public Crop(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crop", namespace, output, registries);
        }

        /**
         * @param id       The recipe id to use.
         * @param input    The input ingredient to use.
         * @param duration The duration to use.
         */
        public Builder builder(String id, Ingredient input, int duration) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, duration);
        }

        @Override
        public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            if (flower instanceof BlockItem bi) {
                builder(toName(flower), ingredient(flower), 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(flower).build();
            }
        }

        @Override
        public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
            if (flower instanceof BlockItem bi && bi.getBlock() instanceof TallFlowerBlock tfb) {
                builder(toName(flower), ingredient(flower), 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)))
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER)))
                    .addOutput(flower).build();
            }
        }

        @Override
        public void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {
            if (mushroom instanceof BlockItem bi) {
                builder(toName(mushroom), ingredient(mushroom), 1200)
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("mulch")
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(mushroom).build();
            }
        }

        @Override
        public void addFungusAndRootsProcessing(Item fungus, Item roots, Identifier nylium, Identifier stem, Identifier wartBlock) {
            if (fungus instanceof BlockItem bi) {
                builder(toName(fungus), ingredient(fungus), 1200)
                    .addCategory(toName(nylium))
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(fungus)
                    .addOutput(stem)
                    .addOutput(wartBlock, 0.25f)
                    .addOutput(SHROOMLIGHT, 0.05f).build();
            }
            if (roots instanceof BlockItem bi) {
                builder(toName(roots), ingredient(roots), 1200)
                    .addCategory(toName(nylium))
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(roots).build();
            }
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final List<DisplayState> display = new ArrayList<>();
            private final List<HarvestEntry> outputs = new ArrayList<>();
            private final Ingredient input;
            private final int duration;
            private int lightLevel = 0;

            protected Builder(Crop provider, Identifier id, Ingredient input, int duration) {
                super(id, provider);
                this.input = input;
                this.duration = duration;
            }

            /**
             * Sets the light level of this recipe.
             *
             * @param lightLevel The light level to use.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a category to this recipe.
             *
             * @param category The category to add.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            /**
             * Adds a display state to this recipe.
             *
             * @param display The display state to add.
             */
            public Builder addDisplay(DisplayState display) {
                this.display.add(display);
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The id of the output item to add.
             * @param chance The chance that this output will be used.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Identifier output, float chance, int minRolls, int maxRolls) {
                outputs.add(new HarvestEntry(output, chance, minRolls, maxRolls));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The id of the output item to add.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Identifier output, int minRolls, int maxRolls) {
                return addOutput(output, 1, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The id of the output item to add.
             * @param chance The chance of this output to occur.
             */
            public Builder addOutput(Identifier output, float chance) {
                return addOutput(output, chance, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The id of the output item to add.
             */
            public Builder addOutput(Identifier output) {
                return addOutput(output, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The output item to add.
             * @param chance The chance that this output will be used.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Item output, float chance, int minRolls, int maxRolls) {
                return addOutput(itemId(output), chance, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The output item to add.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Item output, int minRolls, int maxRolls) {
                return addOutput(output, 1, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output item to add.
             * @param chance The chance of this output to occur.
             */
            public Builder addOutput(Item output, float chance) {
                return addOutput(output, chance, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output item to add.
             */
            public Builder addOutput(Item output) {
                return addOutput(output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("seed", JsonUtil.toJson(input, registries));
                json.addProperty("growthTicks", duration);
                json.add("display", JsonUtil.singleOrArray(JsonUtil.toList(display, registries)));
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
                json.add("drops", JsonUtil.toList(outputs, e -> {
                    JsonObject object = new JsonObject();
                    object.addProperty("chance", e.stack instanceof PotentiallyAbsentItemStack.WithChance withChance ? withChance.chance : 1f);
                    JsonObject output = new JsonObject();
                    output.addProperty("item", e.stack.item.toString());
                    object.add("output", output);
                    return object;
                }));
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crop(namespace, output, registries);
            }
        }
    }

    public static class Soil extends BotanyPotsCompatHandler<Soil.Builder> {
        public Soil(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("soil", namespace, output, registries);
        }

        /**
         * @param id      The recipe id to use.
         * @param input   The input ingredient to use.
         * @param display The {@link DisplayState} to use.
         */
        public Builder builder(String id, Ingredient input, DisplayState display) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, display);
        }

        @Override
        public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
            if (ore instanceof BlockItem bi) {
                builder(toName(ore), ingredient(ore), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore))
                    .build();
            }
            if (deepslateOre instanceof BlockItem bi) {
                builder(toName(deepslateOre), ingredient(deepslateOre), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore))
                    .addCategory("deepslate")
                    .addCategory(toName(deepslateOre))
                    .build();
            }
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (ore instanceof BlockItem bi) {
                builder(toName(ore), ingredient(ore), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore)).build();
            }
            if (deepslateOre instanceof BlockItem bi) {
                builder(toName(deepslateOre), ingredient(deepslateOre), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore))
                    .addCategory("deepslate")
                    .addCategory(toName(deepslateOre)).build();
            }
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            if (wood instanceof BlockItem bi) {
                builder(toName(wood), ingredient(log, wood), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("wood")
                    .addCategory("log")
                    .addCategory("mushroom")
                    .addCategory(toName(wood).replace("_wood", ""))
                    .addCategory(toName(wood)).build();
            }
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final Ingredient input;
            private final DisplayState display;
            private float growthModifier = 1;
            private int lightLevel = 0;

            protected Builder(Soil provider, Identifier id, Ingredient input, DisplayState display) {
                super(id, provider);
                this.input = input;
                this.display = display;
            }

            /**
             * Sets the growth modifier of this recipe.
             *
             * @param growthModifier The growth modifier to use.
             */
            public Builder setGrowthModifier(float growthModifier) {
                this.growthModifier = growthModifier;
                return this;
            }

            /**
             * Sets the light level of this recipe.
             *
             * @param lightLevel The light level to use.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a category to this recipe.
             *
             * @param category The category to add.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.addProperty("growthModifier", growthModifier);
                json.add("display", display.toJson(registries));
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Soil(namespace, output, registries);
            }
        }
    }
}
