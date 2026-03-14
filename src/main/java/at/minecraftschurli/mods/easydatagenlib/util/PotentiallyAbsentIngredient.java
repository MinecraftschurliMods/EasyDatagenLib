package at.minecraftschurli.mods.easydatagenlib.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class that allows mimicking an ingredient of an item that is not present during compile time by simply using the item's registry name.
 * This should not be used outside datagen, as there is no validation whatsoever!
 */
public class PotentiallyAbsentIngredient implements ICustomIngredient {
    private final Identifier[] items;

    protected PotentiallyAbsentIngredient(Identifier... items) {
        this.items = items;
    }

    public static Ingredient of(Identifier... items) {
        return new Ingredient(new PotentiallyAbsentIngredient(items));
    }

    public JsonElement toJson() {
        if (items.length == 1) {
            JsonObject json = new JsonObject();
            json.addProperty("item", items[0].toString());
            return json;
        } else {
            JsonArray array = new JsonArray();
            for (Identifier item : items) {
                JsonObject json = new JsonObject();
                json.addProperty("item", item.toString());
                array.add(json);
            }
            return array;
        }
    }

    @Override
    public boolean test(ItemStack stack) {
        return Arrays.stream(items).anyMatch(stack.typeHolder()::is);
    }

    @Override
    public Stream<Holder<Item>> items() {
        return Stream.empty();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return null;
    }
}
