package at.minecraftschurli.mods.easydatagenlib.mods.patchouli;

import com.google.gson.JsonElement;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Util {
    public static String serializeStack(ItemStackTemplate stack, HolderLookup.Provider registries) {
        StringBuilder builder = new StringBuilder();
        builder.append(Objects.requireNonNull(stack.typeHolder().getKey()).identifier());
        DataComponentPatch patch = stack.components();
        if (!patch.isEmpty()) {
            builder.append('[');
            RegistryOps<JsonElement> ops = registries.createSerializationContext(JsonOps.INSTANCE);
            for (Map.Entry<DataComponentType<?>, Optional<?>> entry : patch.entrySet()) {
                JsonElement key = DataComponentType.CODEC.encodeStart(ops, entry.getKey()).getOrThrow();
                if (entry.getValue().isPresent()) {
                    //noinspection unchecked
                    Codec<Object> codec = (Codec<Object>) entry.getKey().codecOrThrow();
                    JsonElement value = codec.encodeStart(ops, entry.getValue().get()).getOrThrow();
                    builder.append(key.getAsString());
                    builder.append('=');
                    builder.append(value.getAsString());
                    builder.append(',');
                } else {
                    builder.append('!');
                    builder.append(key.getAsString());
                    builder.append(',');
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(']');
        }
        if (stack.count() != 1) {
            builder.append('#').append(stack.count());
        }
        return builder.toString();
    }

    public static Triple<Holder<Item>, DataComponentPatch, Integer> deserializeStack(String string, HolderLookup.Provider registries) {
        StringReader reader = new StringReader(string.trim());
        ItemParser itemParser = new ItemParser(registries);
        try {
            ItemInput result = itemParser.parse(reader);
            int count = 1;
            if (reader.canRead()) {
                reader.expect('#');
                count = reader.readInt();
            }
            return Triple.of(result.item(), result.components(), count);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
