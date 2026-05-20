package at.minecraftschurli.mods.easydatagenlib.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/// {@see [Datapack Registries documentation](https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Datapack-Registries)}
public final class DatapackRegistryGenerator extends DatapackBuiltinEntriesProvider {

    /// @param output The [PackOutput] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param lookupProvider The [HolderLookup.Provider] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param namespace The namespace that should be written to disk.
    /// @param providers The [AbstractDatapackRegistryProvider]s to add to the generator.
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String namespace, AbstractDatapackRegistryProvider<?>... providers) {
        this(output, lookupProvider, Set.of(namespace), List.of(providers));
    }

    /// @param output The [PackOutput] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param lookupProvider The [HolderLookup.Provider] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param namespaces A [Set] of namespaces that should be written to disk.
    /// @param providers The [AbstractDatapackRegistryProvider]s to add to the generator.
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> namespaces, AbstractDatapackRegistryProvider<?>... providers) {
        this(output, lookupProvider, namespaces, List.of(providers));
    }

    /// @param output The [PackOutput] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param lookupProvider The [HolderLookup.Provider] provided by the [net.neoforged.neoforge.data.event.GatherDataEvent].
    /// @param namespaces A [Set] of namespaces that should be written to disk.
    /// @param providers A [List] of [AbstractDatapackRegistryProvider]s to add to the generator.
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> namespaces, List<AbstractDatapackRegistryProvider<?>> providers) {
        super(output, lookupProvider, makeRegistrySetBuilder(providers), namespaces);
    }

    /// @return The [HolderLookup.Provider] associated with this [DatapackRegistryGenerator], encapsulated into a [CompletableFuture].
    @Deprecated
    public CompletableFuture<HolderLookup.Provider> getHolderLookupProvider() {
        return getRegistryProvider();
    }

    /// @return The [DatapackBuiltinEntriesProvider] associated with this [DatapackRegistryGenerator].
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
