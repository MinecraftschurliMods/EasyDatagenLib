package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.FluidIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.create.HeatRequirement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.SerializationException;

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
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The id of the result item to use.
         * @param count The result count to use.
         * @param tag   The result NBT to use.
         */
        public Builder builder(String id, ResourceLocation item, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), item, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The id of the result item to use.
         * @param count The result count to use.
         */
        public Builder builder(String id, ResourceLocation item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id   The id to use.
         * @param item The id of the result item to use.
         */
        public Builder builder(String id, ResourceLocation item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The result item to use.
         * @param count The result count to use.
         * @param tag   The result NBT to use.
         */
        public Builder builder(String id, Item item, int count, CompoundTag tag) {
            return new Builder(new ResourceLocation(namespace, id), item, count, tag);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id    The id to use.
         * @param item  The result item to use.
         * @param count The result count to use.
         */
        public Builder builder(String id, Item item, int count) {
            return new Builder(new ResourceLocation(namespace, id), item, count);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id   The id to use.
         * @param item The result item to use.
         */
        public Builder builder(String id, Item item) {
            return new Builder(new ResourceLocation(namespace, id), item);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> pattern = new ArrayList<>();
            private final Map<Character, Ingredient> key = new HashMap<>();
            private final PotentiallyAbsentItemStack result;
            private boolean acceptMirrored = true;

            public Builder(ResourceLocation id, ResourceLocation item, int count, CompoundTag tag) {
                super(id);
                result = new PotentiallyAbsentItemStack(item, count, tag);
            }

            public Builder(ResourceLocation id, ResourceLocation item, int count) {
                this(id, item, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, ResourceLocation item) {
                this(id, item, 1, new CompoundTag());
            }

            public Builder(ResourceLocation id, Item item, int count, CompoundTag tag) {
                this(id, itemId(item), count, tag);
            }

            public Builder(ResourceLocation id, Item item, int count) {
                this(id, item, count, new CompoundTag());
            }

            public Builder(ResourceLocation id, Item item) {
                this(id, item, 1, new CompoundTag());
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
                json.add("pattern", JsonUtil.toStringList(pattern));
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
                        if (!key.containsKey(c) && c != ' ')
                            throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c + "'");
                        set.remove(c);
                    }
                }
                if (!set.isEmpty())
                    throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
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

        /**
         * Creates a new builder with the given id.
         *
         * @param id               The id to use.
         * @param ingredient       The ingredient to use.
         * @param transitionalItem The id of the transitional item to use.
         */
        public Builder builder(String id, Ingredient ingredient, ResourceLocation transitionalItem) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, transitionalItem);
        }

        /**
         * Creates a new builder with the given id.
         *
         * @param id               The id to use.
         * @param ingredient       The ingredient to use.
         * @param transitionalItem The transitional item to use.
         */
        public Builder builder(String id, Ingredient ingredient, Item transitionalItem) {
            return new Builder(new ResourceLocation(namespace, id), ingredient, transitionalItem);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Processing.Builder> sequence = new ArrayList<>();
            private final List<Pair<PotentiallyAbsentItemStack, Float>> results = new ArrayList<>();
            private final Ingredient ingredient;
            private final PotentiallyAbsentItemStack transitionalItem;
            private int loops = 1;

            public Builder(ResourceLocation id, Ingredient ingredient, ResourceLocation transitionalItem) {
                super(id);
                this.ingredient = ingredient;
                this.transitionalItem = new PotentiallyAbsentItemStack(transitionalItem);
            }

            public Builder(ResourceLocation id, Ingredient ingredient, Item transitionalItem) {
                this(id, ingredient, itemId(transitionalItem));
            }

            /**
             * Sets the amount of loops this recipe will take. Must not be less than 1.
             *
             * @param loops The amount of loops to use.
             * @return This builder, for chaining.
             */
            public Builder setLoops(int loops) {
                if (loops < 1)
                    throw new IllegalArgumentException("Recipe " + id + "has an illegal loop count of " + loops);
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
                json.add("sequence", JsonUtil.toList(sequence, e -> {
                    JsonObject o = new JsonObject();
                    e.toJson(o);
                    return o;
                }));
                json.add("results", JsonUtil.toList(results, e -> {
                    JsonObject o = new JsonObject();
                    o.addProperty("item", e.getFirst().item.toString());
                    o.addProperty("chance", e.getSecond());
                    return o;
                }));
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
         * Creates a new builder with the given id.
         *
         * @param id             The id of the recipe.
         * @param processingTime The processing time of the recipe.
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
            private HeatRequirement heatRequirement = HeatRequirement.NONE;

            public Builder(ResourceLocation id, int processingTime) {
                super(id);
                this.processingTime = processingTime;
            }

            /**
             * Sets the heat requirement of the recipe.
             *
             * @param heatRequirement The heat requirement to set.
             */
            public void setHeatRequirement(HeatRequirement heatRequirement) {
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
             * @param item   The result item to use.
             * @param count  The result count to use.
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
             * @param item  The result item to use.
             * @param count The result count to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, int count) {
                return addResult(item, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item to use.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item, float chance) {
                return addResult(item, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item The result item to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(ResourceLocation item) {
                return addResult(item, 1, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item to use.
             * @param count  The result count to use.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count, float chance) {
                return addResult(itemId(item), count, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item  The result item to use.
             * @param count The result count to use.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, int count) {
                return addResult(item, count, 1);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item   The result item to use.
             * @param chance The chance that the result stack is returned.
             * @return This builder, for chaining.
             */
            public Builder addResult(Item item, float chance) {
                return addResult(item, 1, chance);
            }

            /**
             * Adds a result to the recipe.
             *
             * @param item The result item to use.
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
                if (heatRequirement != HeatRequirement.NONE) {
                    json.addProperty("heatRequirement", heatRequirement.toString());
                }
                json.add("ingredients", JsonUtil.mergeArrays(JsonUtil.toIngredientList(ingredients), JsonUtil.toList(fluidIngredients)));
                json.add("results", JsonUtil.mergeArrays(JsonUtil.toList(results), JsonUtil.toList(fluidResults)));
            }
        }
    }
}
