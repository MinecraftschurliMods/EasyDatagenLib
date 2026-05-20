package at.minecraftschurli.mods.easydatagenlib;

import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/// Extend this class and override [CompatDataProvider#generate(HolderLookup.Provider)] to add your own datagen entries.
@SuppressWarnings({"unused", "DuplicatedCode", "SameParameterValue"})
public abstract class CompatDataProvider implements DataProvider, ICompatHandler {
    private final String namespace;
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private @Nullable Map<Class<? extends ICompatHandler>, ICompatHandler> handlers;

    /// Constructs a new [CompatDataProvider]. Initializes the providers and calls [CompatDataProvider#generate(HolderLookup.Provider)].
    ///
    /// @param namespace The namespace to use. In most cases, this is your own mod id.
    /// @param output    The [DataGenerator] to use. Get this via [GatherDataEvent#getGenerator()].
    protected CompatDataProvider(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.namespace = namespace;
        this.output = output;
        this.registries = registries;
    }

    /// Override this to add your recipes.
    protected abstract void generate(HolderLookup.Provider context);

    @Override
    public final CompletableFuture<?> run(CachedOutput cache) {
        return registries.thenCompose(registries -> {
            ServiceLoader<ICompatHandler.Factory> loader = ServiceLoader.load(FMLLoader.getCurrent().getGameLayer(), ICompatHandler.Factory.class);
            Map<Class<? extends ICompatHandler>, ICompatHandler> handlers = new LinkedHashMap<>();
            for (ICompatHandler.Factory factory : loader) {
                var handler = factory.create(namespace, output, registries);
                if (handlers.put(handler.getClass(), handler) != null) {
                    throw new IllegalStateException("Duplicate ICompatHandler for class " + handler.getClass().getName());
                }
            }
            this.handlers = handlers;
            generate(registries);
            this.handlers = null;
            return CompletableFuture.allOf(handlers.values()
                .stream()
                .map(provider -> provider.run(cache))
                .toArray(CompletableFuture<?>[]::new));
        });
    }

    @Override
    public void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
        forEachHandler(handler -> handler.addGemOreProcessing(ore, deepslateOre, oreTag, gem, block, dust));
    }

    @Override
    public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
        forEachHandler(handler -> handler.addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, rawOreBlock, rawOreBlockTag, ingot, ingotTag, ingotBlock, ingotBlockTag, nugget, nuggetTag, dust, dustTag, crushedOre, secondaryIngot, secondaryDust, secondaryDustTag));
    }

    @Override
    public void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
        forEachHandler(handler -> handler.addFlowerProcessing(flower, output1, count1, output2, count2, chance2, output3, count3, chance3));
    }

    @Override
    public void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {
        forEachHandler(handler -> handler.addTallFlowerProcessing(flower, output1, count1, output2, count2, chance2, output3, count3, chance3));
    }

    @Override
    public void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {
        forEachHandler(handler -> handler.addMushroomProcessing(mushroom, mushroomBlock));
    }

    @Override
    public void addFungusAndRootsProcessing(Item fungus, Item roots, Identifier nylium, Identifier stem, Identifier wartBlock) {
        forEachHandler(handler -> handler.addFungusAndRootsProcessing(fungus, roots, nylium, stem, wartBlock));
    }

    @Override
    public void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
        forEachHandler(handler -> handler.addWoodenProcessing(family, boat, chestBoat, logs));
    }

    @Override
    public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
        forEachHandler(handler -> handler.addLogsProcessing(log, wood, strippedLog, strippedWood, planks, leaves, sapling));
    }

    protected final <T extends ICompatHandler> T getHandler(Class<T> clazz) {
        if (handlers == null) {
            throw new IllegalStateException("Cannot access handlers outside of generation context!");
        }
        return clazz.cast(handlers.get(clazz));
    }

    private void forEachHandler(Consumer<ICompatHandler> consumer) {
        if (handlers == null) {
            throw new IllegalStateException("Cannot access handlers outside of generation context!");
        }
        handlers.values().forEach(consumer);
    }
}
