package at.minecraftschurli.mods.easydatagenlib.util.mekanism;

import at.minecraftschurli.mods.easydatagenlib.util.JsonSerializable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

public abstract class Chemical implements JsonSerializable {
    private final Identifier id;

    public Chemical(Identifier id) {
        this.id = id;
    }

    public abstract String getName();

    @Override
    public JsonElement toJson(HolderLookup.Provider registries) {
        JsonObject json = new JsonObject();
        json.addProperty(getName(), id.toString());
        return json;
    }

    public record Stack<T extends Chemical>(T chemical, int amount) implements JsonSerializable {
        @Override
        public JsonElement toJson(HolderLookup.Provider registries) {
            JsonObject json = chemical.toJson(registries).getAsJsonObject();
            json.addProperty("amount", amount);
            return json;
        }
    }
}
