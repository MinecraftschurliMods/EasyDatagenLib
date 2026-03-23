package at.minecraftschurli.mods.easydatagenlib;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class AbstractModelProvider extends ModelProvider {
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private HolderLookup.@Nullable Provider registries;

    protected AbstractModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, modId);
        this.lookupProvider = lookupProvider;
    }

    protected final HolderLookup.Provider registries() {
        if (registries == null) {
            throw new IllegalStateException("Registries not yet loaded");
        }
        return registries;
    }

    @Override
    protected abstract void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels);

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        HolderLookup<Block> lookup = registries != null ? registries.lookupOrThrow(Registries.BLOCK) : BuiltInRegistries.BLOCK;
        return lookup.listElements().filter(holder -> holder.key().identifier().getNamespace().equals(modId));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        HolderLookup<Item> lookup = registries != null ? registries.lookupOrThrow(Registries.ITEM) : BuiltInRegistries.ITEM;
        return lookup.listElements().filter(holder -> holder.key().identifier().getNamespace().equals(modId));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.lookupProvider.thenCompose(registries -> {
            this.registries = registries;
            return super.run(cache);
        });
    }
}
