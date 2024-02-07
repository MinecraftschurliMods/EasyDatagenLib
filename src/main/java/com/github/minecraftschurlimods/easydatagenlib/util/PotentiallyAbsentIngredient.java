package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.stream.Stream;

/**
 * Class that allows mimicking an ingredient of an item that is not present during compile time by simply using the item's registry name.
 * This should not be used outside datagen, as there is no validation whatsoever!
 */
public class PotentiallyAbsentIngredient extends Ingredient {
    private final ResourceLocation[] items;

    protected PotentiallyAbsentIngredient(ResourceLocation... items) {
        super(Stream.of());
        this.items = items;
    }

    public static PotentiallyAbsentIngredient of(ResourceLocation... items) {
        return new PotentiallyAbsentIngredient(items);
    }

    public JsonElement toJson() {
        if (items.length == 1) {
            JsonObject json = new JsonObject();
            json.addProperty("item", items[0].toString());
            return json;
        } else {
            JsonArray array = new JsonArray();
            for (ResourceLocation item : items) {
                JsonObject json = new JsonObject();
                json.addProperty("item", item.toString());
                array.add(json);
            }
            return array;
        }
    }
}
