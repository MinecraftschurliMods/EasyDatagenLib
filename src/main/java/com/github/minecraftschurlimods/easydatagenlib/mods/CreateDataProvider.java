package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.FluidIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtils;
import com.github.minecraftschurlimods.easydatagenlib.util.ModdedValues;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.apache.commons.lang3.SerializationException;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CreateDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected CreateDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("create", folder), namespace, generator);
    }

    public static class Compacting extends Processing {
        public Compacting(String namespace, DataGenerator generator) {
            super("compacting", namespace, generator);
        }
    }

    public static class Crushing extends Processing {
        public Crushing(String namespace, DataGenerator generator) {
            super("crushing", namespace, generator);
        }
    }

    public static class Cutting extends Processing {
        public Cutting(String namespace, DataGenerator generator) {
            super("cutting", namespace, generator);
        }
    }

    public static class Deploying extends Processing {
        public Deploying(String namespace, DataGenerator generator) {
            super("deploying", namespace, generator);
        }
    }

    public static class Emptying extends Processing {
        public Emptying(String namespace, DataGenerator generator) {
            super("emptying", namespace, generator);
        }
    }

    public static class Filling extends Processing {
        public Filling(String namespace, DataGenerator generator) {
            super("filling", namespace, generator);
        }
    }

    public static class Haunting extends Processing {
        public Haunting(String namespace, DataGenerator generator) {
            super("haunting", namespace, generator);
        }
    }

    public static class ItemApplication extends Processing {
        public ItemApplication(String namespace, DataGenerator generator) {
            super("item_application", namespace, generator);
        }
    }

    public static class MechanicalCrafting extends CreateDataProvider<MechanicalCrafting.Builder> {
        public MechanicalCrafting(String namespace, DataGenerator generator) {
            super("mechanical_crafting", namespace, generator);
        }

        /**
         * @param id   The id of the recipe builder.
         * @param item The result item to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, Item item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The result item to use.
         * @param count The result count to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, Item item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The result item to use.
         * @param count The result count to use.
         * @param tag   The result NBT to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, Item item, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), item, count, tag);
        }

        /**
         * @param id   The id of the recipe builder.
         * @param item The id of the result item to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, ResourceLocation item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The id of the result item to use.
         * @param count The result count to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, ResourceLocation item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * @param id    The id of the recipe builder.
         * @param item  The id of the result item to use.
         * @param count The result count to use.
         * @param tag   The result NBT to use.
         * @return A new recipe builder.
         */
        public Builder builder(String id, ResourceLocation item, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), item, count, tag);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> pattern = new ArrayList<>();
            private final Map<Character, Ingredient> key = new HashMap<>();
            private final PotentiallyAbsentItemStack result;
            private boolean acceptMirrored = true;

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             */
            public Builder(ResourceLocation id, Item item) {
                this(id, item, 1, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             * @param count The result count to use.
             */
            public Builder(ResourceLocation id, Item item, int count) {
                this(id, item, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             * @param count The result count to use.
             * @param tag   The result NBT to use.
             */
            public Builder(ResourceLocation id, Item item, int count, CompoundTag tag) {
                this(id, itemId(item), count, tag);
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             */
            public Builder(ResourceLocation id, ResourceLocation item) {
                this(id, item, 1, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             * @param count The result count to use.
             */
            public Builder(ResourceLocation id, ResourceLocation item, int count) {
                this(id, item, count, new CompoundTag());
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id    The id to use. Should be unique within the same data provider and the same namespace.
             * @param item  The result item id to use.
             * @param count The result count to use.
             * @param tag   The result NBT to use.
             */
            public Builder(ResourceLocation id, ResourceLocation item, int count, CompoundTag tag) {
                super(id);
                result = new PotentiallyAbsentItemStack(item, count, tag);
            }

            /**
             * Sets this recipe to not accept mirrored inputs.
             *
             * @return This builder, for chaining.
             */
            public Builder dontAcceptMirrored() {
                acceptMirrored = false;
                return this;
            }

            /**
             * Adds a pattern line to this recipe.
             *
             * @param pattern The pattern line to add.
             * @return This builder, for chaining.
             */
            public Builder pattern(String pattern) {
                this.pattern.add(pattern);
                return this;
            }

            /**
             * Adds a key to this recipe.
             *
             * @param key   The key to add.
             * @param value The value associated with the key.
             * @return This builder, for chaining.
             */
            public Builder key(char key, Ingredient value) {
                this.key.put(key, value);
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                validate();
                JsonArray pattern = new JsonArray();
                for (String s : this.pattern) {
                    pattern.add(s);
                }
                json.add("pattern", pattern);
                JsonObject key = new JsonObject();
                for (Map.Entry<Character, Ingredient> s : this.key.entrySet()) {
                    json.add(String.valueOf(s.getKey()), s.getValue().toJson());
                }
                json.add("key", key);
                json.add("result", result.toJson());
                json.addProperty("acceptMirrored", acceptMirrored);
            }

            private void validate() {
                if (pattern.isEmpty()) throw new IllegalStateException("No pattern defined for recipe " + id);
                Set<Character> set = new HashSet<>(key.keySet());
                set.remove(' ');
                for (String s : pattern) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (!key.containsKey(c) && c != ' ') throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c + "'");
                        set.remove(c);
                    }
                }
                if (!set.isEmpty()) throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            }
        }
    }

    public static class Milling extends Processing {
        public Milling(String namespace, DataGenerator generator) {
            super("milling", namespace, generator);
        }
    }

    public static class Mixing extends Processing {
        public Mixing(String namespace, DataGenerator generator) {
            super("mixing", namespace, generator);
        }
    }

    public static class Pressing extends Processing {
        public Pressing(String namespace, DataGenerator generator) {
            super("pressing", namespace, generator);
        }
    }

    public static class SandpaperPolishing extends Processing {
        public SandpaperPolishing(String namespace, DataGenerator generator) {
            super("sandpaper_polishing", namespace, generator);
        }
    }

    public static class SequencedAssembly extends CreateDataProvider<SequencedAssembly.Builder> {
        public SequencedAssembly(String namespace, DataGenerator generator) {
            super("sequenced_assembly", namespace, generator);
        }

        public Builder builder(String id, Ingredient ingredient, Item transitionalItem) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, transitionalItem);
        }

        public Builder builder(String id, Ingredient ingredient, ResourceLocation transitionalItem) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, transitionalItem);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Processing.Builder> sequence = new ArrayList<>();
            private final List<Pair<PotentiallyAbsentItemStack, Float>> results = new ArrayList<>();
            private final Ingredient ingredient;
            private final PotentiallyAbsentItemStack transitionalItem;
            private int loops = 1;

            /**
             * Creates a new builder with the given id.
             *
             * @param id               The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient       The ingredient to use.
             * @param transitionalItem The transitional item to use.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, Item transitionalItem) {
                this(id, ingredient, itemId(transitionalItem));
            }

            /**
             * Creates a new builder with the given id.
             *
             * @param id               The id to use. Should be unique within the same data provider and the same namespace.
             * @param ingredient       The ingredient to use.
             * @param transitionalItem The transitional item id to use.
             */
            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation transitionalItem) {
                super(id);
                this.ingredient = ingredient;
                this.transitionalItem = new PotentiallyAbsentItemStack(transitionalItem);
            }

            /**
             * Sets the amount of loops this recipe will take. Must not be less than 1.
             *
             * @param loops The amount of loops to use.
             * @return This builder, for chaining.
             */
            public Builder setLoops(int loops) {
                if (loops < 1) throw new IllegalArgumentException("Recipe " + id + "has an illegal loop count of " + loops);
                this.loops = loops;
                return this;
            }

            /**
             * Adds a processing step to this recipe.
             *
             * @param processing The processing step to add.
             * @return This builder, for chaining.
             */
            public Builder addProcessing(Processing.Builder processing) {
                sequence.add(processing);
                return this;
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The result item to use.
             * @param weight The weight of this output. Only one result item will be the actual output, the weight determines the chance of this happening, compared to other results.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item result, float weight) {
                return addResult(itemId(result), weight);
            }

            /**
             * Adds a result to this recipe.
             *
             * @param result The id of the result item to use.
             * @param weight The weight of this output. Only one result item will be the actual output, the weight determines the chance of this happening, compared to other results.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation result, float weight) {
                results.add(new Pair<>(new PotentiallyAbsentItemStack(result), weight));
                return this;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("ingredient", ingredient.toJson());
                json.add("transitionalItem", transitionalItem.toJson());
                json.addProperty("loops", loops);
                JsonArray sequence = new JsonArray();
                for (Processing.Builder builder : this.sequence) {
                    JsonObject processing = new JsonObject();
                    builder.toJson(processing);
                    sequence.add(processing);
                }
                json.add("sequence", sequence);
                JsonArray results = new JsonArray();
                for (Pair<PotentiallyAbsentItemStack, Float> pair : this.results) {
                    JsonObject result = new JsonObject();
                    result.addProperty("item", pair.getA().getItem().toString());
                    result.addProperty("chance", pair.getB());
                    results.add(result);
                }
                json.add("results", results);
            }
        }
    }

    public static class Splashing extends Processing {
        public Splashing(String namespace, DataGenerator generator) {
            super("splashing", namespace, generator);
        }
    }

    protected static abstract class Processing extends CreateDataProvider<Processing.Builder> {
        protected Processing(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * @param id             The id of the recipe.
         * @param processingTime The processing time of the recipe.
         * @return A new recipe builder.
         */
        public Builder builder(String id, int processingTime) {
            return new Builder(new ResourceLocation(namespace, id), processingTime);
        }

        /**
         * Note: Not all recipes can actually handle all the information. For example, crushing recipes will simply ignore anything fluid-related.
         * However, Create's recipe specification allows everything to be specified for all recipes. Depending on the point of view, this is for the better or worse.
         * {@see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/content/contraptions/processing/ProcessingRecipeBuilder.java}
         */
        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> ingredients = new ArrayList<>();
            private final List<FluidIngredient> fluidIngredients = new ArrayList<>();
            private final List<PotentiallyAbsentItemStack> results = new ArrayList<>();
            private final List<PotentiallyAbsentFluidStack> fluidResults = new ArrayList<>();
            private final int processingTime;
            private boolean keepHeldItem;
            private ModdedValues.Create.HeatRequirement heatRequirement = ModdedValues.Create.HeatRequirement.NONE;

            /**
             * Creates a new builder with the given id.
             *
             * @param id             The id to use. Should be unique within the same data provider and the same namespace.
             * @param processingTime The processing time to use.
             */
            public Builder(ResourceLocation id, int processingTime) {
                super(id);
                this.processingTime = processingTime;
            }

            /**
             * Sets the heat requirement of the recipe.
             *
             * @param heatRequirement The heat requirement to set.
             */
            public void setHeatRequirement(ModdedValues.Create.HeatRequirement heatRequirement) {
                this.heatRequirement = heatRequirement;
            }

            /**
             * Sets this recipe's keepHeldItem property to true.
             */
            public void keepHeldItem() {
                keepHeldItem = true;
            }

            /**
             * Adds an ingredient to the recipe.
             *
             * @param ingredient The ingredient to set.
             * @return This builder, for chaining.
             */
            public Builder addIngredient(Ingredient ingredient) {
                ingredients.add(ingredient);
                return this;
            }

            /**
             * Adds an ingredient to the recipe.
             *
             * @param ingredient The ingredient to set.
             * @return This builder, for chaining.
             */
            public Builder addIngredient(FluidIngredient ingredient) {
                fluidIngredients.add(ingredient);
                return this;
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item.
             * @param count  The result count.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count, float chance) {
                results.add(new PotentiallyAbsentItemStack.WithChance(item, count, chance));
                return this;
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item  The result item.
             * @param count The result count.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count) {
                return addResult(item, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, float chance) {
                return addResult(item, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item The result item.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item) {
                return addResult(item, 1, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item.
             * @param count  The result count.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count, float chance) {
                return addResult(itemId(item), count, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item  The result item.
             * @param count The result count.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count) {
                return addResult(item, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, float chance) {
                return addResult(item, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item The result item.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item) {
                return addResult(item, 1, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid  The result fluid.
             * @param count  The result count.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(ResourceLocation fluid, int count, float chance) {
                fluidResults.add(new PotentiallyAbsentFluidStack.WithChance(fluid, count, chance));
                return this;
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid The result fluid.
             * @param count The result count.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(ResourceLocation fluid, int count) {
                return addFluidResult(fluid, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid  The result fluid.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(ResourceLocation fluid, float chance) {
                return addFluidResult(fluid, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid The result fluid.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(ResourceLocation fluid) {
                return addFluidResult(fluid, 1, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid  The result fluid.
             * @param count  The result count.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(Fluid fluid, int count, float chance) {
                return addFluidResult(fluidId(fluid), count, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid The result fluid.
             * @param count The result count.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(Fluid fluid, int count) {
                return addFluidResult(fluid, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid  The result fluid.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(Fluid fluid, float chance) {
                return addFluidResult(fluid, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param fluid The result fluid.
             * @return This builder, for chaining.
             */
            public Builder addFluidResult(Fluid fluid) {
                return addFluidResult(fluid, 1, 1);
            }

            @Override
            protected void toJson(JsonObject json) {
                if (processingTime <= 0)
                    throw new SerializationException("This recipe needs a duration of at least 1!");
                json.addProperty("processingTime", processingTime);
                if (keepHeldItem) {
                    json.addProperty("keepHeldItem", true);
                }
                if (heatRequirement != ModdedValues.Create.HeatRequirement.NONE) {
                    json.addProperty("heatRequirement", heatRequirement.toString());
                }
                JsonUtils.addIngredientsToJson(json, ingredients, fluidIngredients);
                JsonUtils.addResultsToJson(json, results, fluidResults);
            }
        }
    }
}
