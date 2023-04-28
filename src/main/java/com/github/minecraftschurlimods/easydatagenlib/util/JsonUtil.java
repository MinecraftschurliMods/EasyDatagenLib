package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.function.Function;

public class JsonUtil {
    /**
     * @param arrays The {@link JsonArray}s to merge.
     * @return The merged {@link JsonArray}.
     */
    public static JsonArray mergeArrays(JsonArray... arrays) {
        JsonArray result = new JsonArray();
        for (JsonArray array : arrays) {
            result.addAll(array);
        }
        return result;
    }

    /**
     * @param array The {@link JsonArray} to handle.
     * @return If the {@link JsonArray} contains one element, the element is returned. Otherwise, the {@link JsonArray} is returned.
     */
    public static JsonElement singleOrArray(JsonArray array) {
        return array.size() == 1 ? array.get(0) : array;
    }

    /**
     * @param list A list.
     * @param function A function that determines how the list elements will be converted into JSON.
     * @return A {@link JsonArray}, constructed from the given list.
     * @param <T> The list element type.
     */
    public static <T> JsonArray toList(List<T> list, Function<? super T, JsonElement> function) {
        JsonArray array = new JsonArray();
        list.forEach(e -> array.add(function.apply(e)));
        return array;
    }

    /**
     * @param list A list of {@link JsonSerializable}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toList(List<? extends JsonSerializable> list) {
        return toList(list, JsonSerializable::toJson);
    }

    /**
     * @param list A list of {@link Boolean}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toBooleanList(List<Boolean> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link Number}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toNumberList(List<Number> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link String}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toStringList(List<String> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link Character}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toCharList(List<Character> list) {
        return toStringList(list.stream().map(Object::toString).toList());
    }

    /**
     * @param list A list of {@link Enum} values.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toEnumList(List<? extends Enum<?>> list) {
        return toStringList(list.stream().map(Enum::name).toList());
    }

    /**
     * @param list A list of {@link Ingredient}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toIngredientList(List<? extends Ingredient> list) {
        return toList(list, Ingredient::toJson);
    }
}
