package at.minecraftschurli.mods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/// The abstract class all other data provider classes extend from. Contains some common functionality, such as saving to disk.
///
/// @param <T> The builder class associated with this provider.
/// @see [Custom Datagen Base Classes documentation](https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes)
public abstract class AbstractCompatHandler<T extends AbstractDataBuilder<?>> implements ICompatHandler {
    protected final String namespace;
    protected final PackOutput.PathProvider pathProvider;
    protected final List<T> values = new ArrayList<>();
    private final HolderLookup.Provider registries;

    /// @param output    The pack output to use.
    /// @param namespace The namespace to use.
    /// @param folder    The folder to output to.
    protected AbstractCompatHandler(String namespace, String folder, PackOutput.Target target, PackOutput output, HolderLookup.Provider registries) {
        this(namespace, output.createPathProvider(target, folder), registries);
    }

    /// @param namespace    The namespace to use.
    /// @param pathProvider The provider for the output file paths.
    protected AbstractCompatHandler(String namespace, PackOutput.PathProvider pathProvider, HolderLookup.Provider registries) {
        this.namespace = namespace;
        this.pathProvider = pathProvider;
        this.registries = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Set<Identifier> ids = new HashSet<>();
        List<CompletableFuture<?>> list = new ArrayList<>();
        values.forEach(o -> {
            if (!ids.add(o.id)) throw new IllegalStateException("Duplicate datagenned object " + o.id);
            list.add(DataProvider.saveStable(output, toJson(o, registries), pathProvider.json(o.id)));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    /// @return The name of this data provider, for use in logging.
    ///
    /// Should look something like this: `return "TheThingsBeingDatagenned[" + namespace + "]";`
    public abstract String getName();

    /// Adds the given builder to the generator.
    ///
    /// @param builder The builder to add.
    protected void add(T builder) {
        values.add(builder);
    }

    /// Removes a recipe with the given id from the generator.
    ///
    /// @param id The id of the recipe to remove.
    public void remove(Identifier id) {
        values.removeIf(e -> e.id.toString().equals(id.toString()));
    }

    /// Constructs a [JsonObject] from the given builder.
    ///
    /// @param builder The builder to construct the [JsonObject] from.
    /// @return A [JsonObject], constructed from the given builder.
    protected JsonObject toJson(T builder, HolderLookup.Provider registries) {
        JsonObject json = new JsonObject();
        builder.toJson(json, registries);
        return json;
    }

    /// Shortcut to get a block's registry name.
    ///
    /// @param block The block to get the registry name for.
    /// @return The registry name of the given block.
    @SuppressWarnings("ConstantConditions")
    protected static Identifier blockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /// Shortcut to get an item's registry name.
    ///
    /// @param item The item to get the registry name for.
    /// @return The registry name of the given item.
    @SuppressWarnings("ConstantConditions")
    protected static Identifier itemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    /// Shortcut to get a fluid's registry name.
    ///
    /// @param fluid The fluid to get the registry name for.
    /// @return The registry name of the given fluid.
    @SuppressWarnings("ConstantConditions")
    protected static Identifier fluidId(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    protected final Ingredient ingredient(ItemLike... item) {
        return Ingredient.of(item);
    }

    protected final Ingredient ingredient(TagKey<Item> tag) {
        return Ingredient.of(HolderSet.emptyNamed(registries.lookupOrThrow(tag.registry()), tag));
    }
}
