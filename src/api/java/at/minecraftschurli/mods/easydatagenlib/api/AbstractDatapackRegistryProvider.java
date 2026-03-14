package at.minecraftschurli.mods.easydatagenlib.api;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Datapack-Registries">Datapack Registries documentation</a>}
 */
public abstract class AbstractDatapackRegistryProvider<T> {
    public final ResourceKey<? extends Registry<T>> registryKey;
    public final String namespace;
    private @Nullable BootstrapContext<T> bootstrapContext;

    /**
     * @param registryKey The {@link ResourceKey} to use.
     * @param namespace   The name to use.
     */
    public AbstractDatapackRegistryProvider(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this.registryKey = registryKey;
        this.namespace = namespace;
    }

    /**
     * Override this method to add your to-be-datagenned objects.
     */
    public abstract void generate();

    /**
     * Gets the {@link HolderSet} for the given {@link TagKey}.
     *
     * @param tagKey The {@link TagKey} to get the {@link HolderSet} for.
     * @param <S>    The type of the {@link HolderSet}.
     * @return The {@link HolderSet} for the given {@link TagKey}.
     */
    protected final <S> HolderSet.Named<S> tag(TagKey<S> tagKey) {
        return getLookup(tagKey.registry()).getOrThrow(tagKey);
    }

    /**
     * Gets the {@link HolderSet} for the given {@link TagKey}, or {@link Optional#empty()} if absent.
     *
     * @param tagKey The {@link TagKey} to get the {@link HolderSet} for.
     * @param <S>    The type of the {@link HolderSet}.
     * @return The {@link HolderSet} for the given {@link TagKey}.
     */
    protected final <S> Optional<HolderSet.Named<S>> optionalTag(TagKey<S> tagKey) {
        return getLookup(tagKey.registry()).get(tagKey);
    }

    /**
     * Gets the {@link Holder} for the given {@link ResourceKey}.
     *
     * @param key The {@link ResourceKey} to get the {@link Holder} for.
     * @param <S> The type of the {@link Holder}.
     * @return The {@link Holder} for the given {@link ResourceKey}.
     */
    protected final <S> Holder.Reference<S> holder(ResourceKey<S> key) {
        return getLookup(ResourceKey.<S>createRegistryKey(key.registry())).getOrThrow(key);
    }

    /**
     * Gets the {@link Holder} for the given {@link Identifier} from the given {@link ResourceKey}'s registry.
     *
     * @param registryKey The {@link ResourceKey} to get the {@link Holder} from.
     * @param key         The {@link Identifier} to get the {@link Holder} for.
     * @param <S>         The type of the {@link Holder}.
     * @return The {@link Holder} for the given {@link ResourceKey}.
     */
    protected final <S> Holder.Reference<S> holder(ResourceKey<? extends Registry<S>> registryKey, Identifier key) {
        return getLookup(registryKey).getOrThrow(ResourceKey.create(registryKey, key));
    }

    /**
     * Gets the {@link Holder} for the given {@link Identifier} from the given {@link ResourceKey}'s registry.
     *
     * @param registryKey The {@link ResourceKey} to get the {@link Holder} from.
     * @param name        The path of the {@link Identifier} to get the {@link Holder} for.
     * @param <S>         The type of the {@link Holder}.
     * @return The {@link Holder} for the given {@link ResourceKey}.
     */
    protected final <S> Holder.Reference<S> holder(ResourceKey<? extends Registry<S>> registryKey, String name) {
        return holder(registryKey, Identifier.fromNamespaceAndPath(namespace, name));
    }

    /**
     * Get the {@link Holder} for the given {@link ResourceKey}, or {@link Optional#empty()} if absent.
     *
     * @param key The {@link ResourceKey} to get the {@link Holder} for.
     * @param <S> The type of the {@link Holder}.
     * @return The {@link Holder} for the given {@link ResourceKey}.
     */
    protected final <S> Optional<Holder.Reference<S>> optionalHolder(ResourceKey<S> key) {
        return getLookup(ResourceKey.<S>createRegistryKey(key.registry())).get(key);
    }

    /**
     * Get the {@link Holder} for the given {@link Identifier} from this builder's current registry.
     *
     * @param id The {@link Identifier} to get the {@link Holder} for.
     * @return The {@link Holder} for the given {@link Identifier}.
     */
    protected final Holder.Reference<T> ownHolder(Identifier id) {
        return holder(ResourceKey.create(registryKey, id));
    }

    /**
     * Get the {@link Holder} for the given name from this builder's current registry and mod id.
     *
     * @param name The name to get the {@link Holder} for.
     * @return The {@link Holder} for the given {@link Identifier}.
     */
    protected final Holder.Reference<T> ownHolder(String name) {
        return ownHolder(Identifier.fromNamespaceAndPath(namespace, name));
    }

    /**
     * Get the {@link HolderGetter} for the registry of the given {@link ResourceKey}.
     *
     * @param registryKey The {@link ResourceKey} of the registry to get the {@link HolderGetter} for.
     * @param <S>         The type of the registry.
     * @return The {@link HolderGetter} for the registry of the given {@link ResourceKey}.
     */
    protected final <S> HolderGetter<S> getLookup(ResourceKey<? extends Registry<S>> registryKey) {
        if (bootstrapContext == null) throw new IllegalStateException("Cannot get lookup before generate()");
        return bootstrapContext.lookup(registryKey);
    }

    /**
     * Registers a value to the datapack registry.
     *
     * @param name  The name of the value.
     * @param value The value.
     */
    protected final void add(String name, T value) {
        add(Identifier.fromNamespaceAndPath(namespace, name), value);
    }

    /**
     * Registers a value to the datapack registry.
     *
     * @param id    The id of the value.
     * @param value The value.
     */
    protected final void add(Identifier id, T value) {
        if (bootstrapContext == null) throw new IllegalStateException("Cannot add before generate()");
        bootstrapContext.register(ResourceKey.create(registryKey, id), value);
    }

    void run(BootstrapContext<T> bootstrapContext) {
        this.bootstrapContext = bootstrapContext;
        generate();
    }
}
