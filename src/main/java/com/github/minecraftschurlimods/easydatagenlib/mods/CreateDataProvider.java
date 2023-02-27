package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.FluidIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.ModdedValues;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.SerializationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //TODO mechanical crafting

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

    //TODO sequenced assembly

    public static class Splashing extends Processing {
        public Splashing(String namespace, DataGenerator generator) {
            super("splashing", namespace, generator);
        }
    }

    private static abstract class Processing extends CreateDataProvider<Processing.Builder> {
        protected Processing(String folder, String namespace, DataGenerator generator) {
            super(folder, namespace, generator);
        }

        /**
         * @param id             The id of the recipe.
         * @param processingTime The processing time of the recipe.
         * @return A new recipe builder.
         */
        public Builder builder(String id, int processingTime) {
            return new Builder(folder, new ResourceLocation(namespace, id)).setProcessingTime(processingTime);
        }

        /**
         * Note: Not all recipes can actually handle all the information. For example, crushing recipes will simply ignore anything fluid-related.
         * However, Create's recipe specification allows everything to be specified for all recipes. Depending on the point of view, this is for the better or worse.
         * {@see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/content/contraptions/processing/ProcessingRecipeBuilder.java}
         */
        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final String folder;
            private final List<Ingredient> ingredients = new ArrayList<>();
            private final List<FluidIngredient> fluidIngredients = new ArrayList<>();
            private final List<PotentiallyAbsentItemStack> results = new ArrayList<>();
            private final List<PotentiallyAbsentFluidStack> fluidResults = new ArrayList<>();
            private int processingTime;
            private boolean keepHeldItem;
            private ModdedValues.Create.HeatRequirement heatRequirement = ModdedValues.Create.HeatRequirement.NONE;

            public Builder(String folder, ResourceLocation id) {
                super(id);
                this.folder = folder;
            }

            /**
             * Sets the processing time of the recipe.
             *
             * @param processingTime The processing time to set.
             * @return This builder, for chaining.
             */
            public Builder setProcessingTime(int processingTime) {
                this.processingTime = processingTime;
                return this;
            }

            /**
             * Sets this recipe's keepHeldItem property to true.
             */
            public void keepHeldItem() {
                keepHeldItem = true;
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
                return addResult(Objects.requireNonNull(item.getRegistryName()), count, chance);
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
                return addFluidResult(Objects.requireNonNull(fluid.getRegistryName()), count, chance);
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
                JsonArray ingredients = new JsonArray();
                for (Ingredient ingredient : this.ingredients) {
                    ingredients.add(ingredient.toJson());
                }
                for (FluidIngredient ingredient : fluidIngredients) {
                    ingredients.add(ingredient.toJson());
                }
                json.add("ingredients", ingredients);
                JsonArray results = new JsonArray();
                for (PotentiallyAbsentItemStack stack : this.results) {
                    results.add(stack.toJson());
                }
                for (PotentiallyAbsentFluidStack stack : fluidResults) {
                    results.add(stack.toJson());
                }
                json.add("results", results);
            }
        }
    }
}
