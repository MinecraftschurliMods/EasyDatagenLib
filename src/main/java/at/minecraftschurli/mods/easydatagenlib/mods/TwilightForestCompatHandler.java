package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public abstract class TwilightForestCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected TwilightForestCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("twilightforest", folder), namespace, output, registries);
    }

    public static class Crumbling extends TwilightForestCompatHandler<Crumbling.Builder> {
        public Crumbling(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crumble_horn", namespace, output, registries);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The id of the base block to use.
         * @param to   The id of the result block to use.
         */
        public Builder builder(String id, Identifier from, Identifier to) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The id of the base block to use.
         * @param to   The result block to use.
         */
        public Builder builder(String id, Identifier from, Block to) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The base block to use.
         * @param to   The id of the result block to use.
         */
        public Builder builder(String id, Block from, Identifier to) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The base block to use.
         * @param to   The result block to use.
         */
        public Builder builder(String id, Block from, Block to) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Identifier from;
            private final Identifier to;

            protected Builder(Crumbling provider, Identifier id, Identifier from, Identifier to) {
                super(id, provider);
                this.from = from;
                this.to = to;
            }

            protected Builder(Crumbling provider, Identifier id, Identifier from, Block to) {
                this(provider, id, from, blockId(to));
            }

            protected Builder(Crumbling provider, Identifier id, Block from, Identifier to) {
                this(provider, id, blockId(from), to);
            }

            protected Builder(Crumbling provider, Identifier id, Block from, Block to) {
                this(provider, id, blockId(from), blockId(to));
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("from", from.toString());
                json.addProperty("to", to.toString());
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crumbling(namespace, output, registries);
            }
        }
    }

    public static class Transforming extends TwilightForestCompatHandler<Transforming.Builder> {
        public Transforming(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("transformation_powder", namespace, output, registries);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The id of the base entity type to use.
         * @param to         The id of the result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, Identifier from, Identifier to, boolean reversible) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The id of the base entity type to use.
         * @param to         The result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, Identifier from, EntityType<?> to, boolean reversible) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The base entity type to use.
         * @param to         The id of the result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, EntityType<?> from, Identifier to, boolean reversible) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The base entity type to use.
         * @param to         The result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, EntityType<?> from, EntityType<?> to, boolean reversible) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), from, to, reversible);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Identifier from;
            private final Identifier to;
            private final boolean reversible;

            protected Builder(Transforming provider, Identifier id, Identifier from, Identifier to, boolean reversible) {
                super(id, provider);
                this.from = from;
                this.to = to;
                this.reversible = reversible;
            }

            protected Builder(Transforming provider, Identifier id, Identifier from, EntityType<?> to, boolean reversible) {
                this(provider, id, from, Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(to)), reversible);
            }

            protected Builder(Transforming provider, Identifier id, EntityType<?> from, Identifier to, boolean reversible) {
                this(provider, id, Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(from)), to, reversible);
            }

            protected Builder(Transforming provider, Identifier id, EntityType<?> from, EntityType<?> to, boolean reversible) {
                this(provider, id, Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(from)), Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(to)), reversible);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("reversible", reversible);
                json.addProperty("from", from.toString());
                json.addProperty("to", to.toString());
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Transforming(namespace, output, registries);
            }
        }
    }
}
