package com.github.minecraftschurlimods.easydatagenlib.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Datapack-Registries">Datapack Registries documentation</a>}
 */
public final class DatapackRegistryGenerator extends DatapackBuiltinEntriesProvider {

    /**
     * @param output The {@link PackOutput} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param lookupProvider The {@link HolderLookup.Provider} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param namespace The namespace that should be written to disk.
     * @param providers The {@link AbstractDatapackRegistryProvider}s to add to the generator.
     */
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String namespace, AbstractDatapackRegistryProvider<?>... providers) {
        this(output, lookupProvider, Set.of(namespace), List.of(providers));
    }

    /**
     * @param output The {@link PackOutput} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param lookupProvider The {@link HolderLookup.Provider} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param namespaces A {@link Set} of namespaces that should be written to disk.
     * @param providers The {@link AbstractDatapackRegistryProvider}s to add to the generator.
     */
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> namespaces, AbstractDatapackRegistryProvider<?>... providers) {
        this(output, lookupProvider, namespaces, List.of(providers));
    }

    /**
     * @param output The {@link PackOutput} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param lookupProvider The {@link HolderLookup.Provider} provided by the {@link net.neoforged.neoforge.data.event.GatherDataEvent}.
     * @param namespaces A {@link Set} of namespaces that should be written to disk.
     * @param providers A {@link List} of {@link AbstractDatapackRegistryProvider}s to add to the generator.
     */
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> namespaces, List<AbstractDatapackRegistryProvider<?>> providers) {
        super(output, lookupProvider, makeRegistrySetBuilder(providers), namespaces);
    }

    /**
     * @return The {@link HolderLookup.Provider} associated with this {@link DatapackRegistryGenerator}, encapsulated into a {@link CompletableFuture}.
     */
    @Deprecated
    public CompletableFuture<HolderLookup.Provider> getHolderLookupProvider() {
        return getRegistryProvider();
    }

    /**
     * @return The {@link DatapackBuiltinEntriesProvider} associated with this {@link DatapackRegistryGenerator}.
     */
    @Deprecated
    public DatapackBuiltinEntriesProvider getDatapackBuiltinEntriesProvider() {
        return this;
    }

    private static RegistrySetBuilder makeRegistrySetBuilder(List<AbstractDatapackRegistryProvider<?>> list) {
        RegistrySetBuilder builder = new RegistrySetBuilder();
        for (AbstractDatapackRegistryProvider<?> provider : list) {
            add(builder, provider);
        }
        return builder;
    }

    private static <T> void add(RegistrySetBuilder builder, AbstractDatapackRegistryProvider<T> provider) {
        builder.add(provider.registryKey, provider::run);
    }
}
