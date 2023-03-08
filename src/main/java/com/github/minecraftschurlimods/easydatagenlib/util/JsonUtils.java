package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class JsonUtils {
    /**
     * Adds a {@link JsonArray} containing the JSON representations of the given {@link Ingredient}s to the given {@link JsonObject}.
     *
     * @param json        The {@link JsonObject} to add the {@link JsonArray} to.
     * @param ingredients The {@link List} of {@link Ingredient}s to use.
     */
    public static void addIngredientsToJson(JsonObject json, List<Ingredient> ingredients) {
        JsonArray array = new JsonArray();
        for (Ingredient i : ingredients) {
            array.add(i.toJson());
        }
        json.add("ingredients", array);
    }

    /**
     * Adds a {@link JsonArray} containing the JSON representations of the given {@link Ingredient}s and the given {@link FluidIngredient}s to the given {@link JsonObject}.
     *
     * @param json             The {@link JsonObject} to add the {@link JsonArray} to.
     * @param ingredients      The {@link List} of {@link Ingredient}s to use.
     * @param fluidIngredients The {@link List} of {@link FluidIngredient}s to use.
     */
    public static void addIngredientsToJson(JsonObject json, List<Ingredient> ingredients, List<FluidIngredient> fluidIngredients) {
        JsonArray array = new JsonArray();
        for (Ingredient i : ingredients) {
            array.add(i.toJson());
        }
        for (FluidIngredient i : fluidIngredients) {
            array.add(i.toJson());
        }
        json.add("ingredients", array);
    }

    /**
     * Adds a {@link JsonArray} containing the JSON representations of the given {@link PotentiallyAbsentItemStack}s to the given {@link JsonObject}.
     *
     * @param json    The {@link JsonObject} to add the {@link JsonArray} to.
     * @param results The {@link List} of {@link PotentiallyAbsentItemStack}s to use.
     */
    public static void addResultsToJson(JsonObject json, List<PotentiallyAbsentItemStack> results) {
        JsonArray array = new JsonArray();
        for (PotentiallyAbsentItemStack r : results) {
            array.add(r.toJson());
        }
        json.add("results", array);
    }

    /**
     * Adds a {@link JsonArray} containing the JSON representations of the given {@link PotentiallyAbsentItemStack}s and the given {@link PotentiallyAbsentFluidStack}s to the given {@link JsonObject}.
     *
     * @param json         The {@link JsonObject} to add the {@link JsonArray} to.
     * @param results      The {@link List} of {@link PotentiallyAbsentItemStack}s to use.
     * @param fluidResults The {@link List} of {@link PotentiallyAbsentFluidStack}s to use.
     */
    public static void addResultsToJson(JsonObject json, List<PotentiallyAbsentItemStack> results, List<PotentiallyAbsentFluidStack> fluidResults) {
        JsonArray array = new JsonArray();
        for (PotentiallyAbsentItemStack r : results) {
            array.add(r.toJson());
        }
        for (PotentiallyAbsentFluidStack r : fluidResults) {
            array.add(r.toJson());
        }
        json.add("results", array);
    }
}
