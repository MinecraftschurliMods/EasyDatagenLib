package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes">Custom Datagen Base Classes documentation</a>}
 */
public abstract class AbstractDataProvider<T extends AbstractDataBuilder<?>> implements DataProvider {
    protected final String folder;
    protected final String namespace;
    protected final DataGenerator generator;
    protected final List<T> values = new ArrayList<>();

    /**
     * @param generator The data generator to use.
     * @param namespace The namespace to use.
     * @param folder    The folder to output to.
     */
    protected AbstractDataProvider(String folder, String namespace, DataGenerator generator) {
        this.folder = folder;
        this.namespace = namespace;
        this.generator = generator;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Set<ResourceLocation> ids = new HashSet<>();
        List<CompletableFuture<?>> list = new ArrayList<>();
        values.forEach(o -> {
            if (!ids.add(o.id)) throw new IllegalStateException("Duplicate datagenned object " + o.id);
            list.add(DataProvider.saveStable(output, toJson(o), generator.getPackOutput().getOutputFolder().resolve("data/" + o.id.getNamespace() + "/" + folder + "/" + o.id.getPath() + ".json")));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    /**
     * @return The name of this data provider, for use in logging.<br>
     * Should look something like this: {@code return "TheThingsBeingDatagenned[" + namespace + "]";}
     */
    public abstract String getName();

    /**
     * Adds the given builder to the generator.
     *
     * @param builder The builder to add.
     */
    public void add(T builder) {
        values.add(builder);
    }

    /**
     * Removes a recipe with the given id from the generator.
     *
     * @param id The id of the recipe to remove.
     */
    public void remove(ResourceLocation id) {
        values.removeIf(e -> e.id.toString().equals(id.toString()));
    }

    /**
     * Constructs a {@link JsonObject} from the given builder.
     *
     * @param builder The builder to construct the {@link JsonObject} from.
     * @return A {@link JsonObject}, constructed from the given builder.
     */
    protected JsonObject toJson(T builder) {
        JsonObject json = new JsonObject();
        builder.toJson(json);
        return json;
    }
}
