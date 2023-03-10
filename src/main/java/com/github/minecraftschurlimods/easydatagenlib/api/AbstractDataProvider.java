package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The abstract class all other data provider classes extend from. Contains some common functionality, such as saving to disk.
 *
 * @param <T> The builder class associated with this provider.
 */
public abstract class AbstractDataProvider<T extends AbstractDataBuilder<?>> implements DataProvider {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected static final Logger LOGGER = LogManager.getLogger();
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
    public void run(HashCache cache) {
        Set<ResourceLocation> ids = new HashSet<>();
        values.forEach(o -> {
            if (!ids.add(o.id)) throw new IllegalStateException("Duplicate datagenned object " + o.id);
            try {
                DataProvider.save(GSON, cache, toJson(o), generator.getOutputFolder().resolve("data/" + o.id.getNamespace() + "/" + folder + "/" + o.id.getPath() + ".json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
