package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * The abstract class all other data provider classes extend from. Contains some common functionality, such as saving to disk.
 *
 * @param <T> The builder class associated with this provider.
 * @see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes">Custom Datagen Base Classes documentation</a>
 */
public abstract class AbstractDataProvider<T extends AbstractDataBuilder<?>> implements DataProvider {
    protected final String namespace;
    protected final PackOutput.PathProvider pathProvider;
    protected final List<T> values = new ArrayList<>();

    /**
     * @param output    The pack output to use.
     * @param namespace The namespace to use.
     * @param folder    The folder to output to.
     */
    protected AbstractDataProvider(String namespace, String folder, PackOutput.Target target, PackOutput output) {
        this(namespace, output.createPathProvider(target, folder));
    }

    /**
     * @param namespace    The namespace to use.
     * @param pathProvider The provider for the output file paths.
     */
    protected AbstractDataProvider(String namespace, PackOutput.PathProvider pathProvider) {
        this.namespace = namespace;
        this.pathProvider = pathProvider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Set<ResourceLocation> ids = new HashSet<>();
        List<CompletableFuture<?>> list = new ArrayList<>();
        values.forEach(o -> {
            if (!ids.add(o.id)) throw new IllegalStateException("Duplicate datagenned object " + o.id);
            list.add(DataProvider.saveStable(output, toJson(o), pathProvider.json(o.id)));
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
    protected void add(T builder) {
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

    /**
     * Shortcut to get a block's registry name.
     *
     * @param block The block to get the registry name for.
     * @return The registry name of the given block.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation blockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    /**
     * Shortcut to get an item's registry name.
     *
     * @param item The item to get the registry name for.
     * @return The registry name of the given item.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation itemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    /**
     * Shortcut to get a fluid's registry name.
     *
     * @param fluid The fluid to get the registry name for.
     * @return The registry name of the given fluid.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation fluidId(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }
}
