package com.github.minecraftschurlimods.easydatagenlib.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Datapack-Registries">Datapack Registries documentation</a>}
 */
public final class DatapackRegistryGenerator implements DataProvider {
    private final RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
    private final DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider;
    private final CompletableFuture<HolderLookup.Provider> holderLookupProvider;

    /**
     * @param output The {@link PackOutput} provided by the {@link net.minecraftforge.data.event.GatherDataEvent}.
     * @param lookupProvider The {@link HolderLookup.Provider} provided by the {@link net.minecraftforge.data.event.GatherDataEvent}.
     * @param namespaces A {@link Set} of namespaces that should be written to disk.
     * @param list A {@link List} of {@link AbstractDatapackRegistryProvider}s to add to the generator.
     */
    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> namespaces, List<AbstractDatapackRegistryProvider<?>> list) {
        list.forEach(this::add);
        this.datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySetBuilder, namespaces);
        this.holderLookupProvider = lookupProvider.thenApply(lp -> registrySetBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), lp));
    }

    /**
     * @return The {@link HolderLookup.Provider} associated with this {@link DatapackRegistryGenerator}, encapsulated into a {@link CompletableFuture}.
     */
    public CompletableFuture<HolderLookup.Provider> getHolderLookupProvider() {
        return holderLookupProvider;
    }

    /**
     * @return The {@link DatapackBuiltinEntriesProvider} associated with this {@link DatapackRegistryGenerator}.
     */
    public DatapackBuiltinEntriesProvider getDatapackBuiltinEntriesProvider() {
        return datapackBuiltinEntriesProvider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return datapackBuiltinEntriesProvider.run(pOutput);
    }

    @Override
    public String getName() {
        return datapackBuiltinEntriesProvider.getName();
    }

    private <T> void add(AbstractDatapackRegistryProvider<T> builder) {
        registrySetBuilder.add(builder.registryKey, builder::run);
    }
}
