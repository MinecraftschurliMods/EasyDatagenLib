package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public abstract class TwilightForestDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected TwilightForestDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("twilightforest", folder), namespace, generator);
    }

    public static class Crumbling extends TwilightForestDataProvider<Crumbling.Builder> {
        public Crumbling(String namespace, DataGenerator generator) {
            super("crumble_horn", namespace, generator);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The id of the base block to use.
         * @param to   The id of the result block to use.
         */
        public Builder builder(String id, ResourceLocation from, ResourceLocation to) {
            return new Builder(new ResourceLocation(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The id of the base block to use.
         * @param to   The result block to use.
         */
        public Builder builder(String id, ResourceLocation from, Block to) {
            return new Builder(new ResourceLocation(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The base block to use.
         * @param to   The id of the result block to use.
         */
        public Builder builder(String id, Block from, ResourceLocation to) {
            return new Builder(new ResourceLocation(namespace, id), from, to);
        }

        /**
         * @param id   The recipe id to use.
         * @param from The base block to use.
         * @param to   The result block to use.
         */
        public Builder builder(String id, Block from, Block to) {
            return new Builder(new ResourceLocation(namespace, id), from, to);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final ResourceLocation from;
            private final ResourceLocation to;

            public Builder(ResourceLocation id, ResourceLocation from, ResourceLocation to) {
                super(id);
                this.from = from;
                this.to = to;
            }

            public Builder(ResourceLocation id, ResourceLocation from, Block to) {
                this(id, from, blockId(to));
            }

            public Builder(ResourceLocation id, Block from, ResourceLocation to) {
                this(id, blockId(from), to);
            }

            public Builder(ResourceLocation id, Block from, Block to) {
                this(id, blockId(from), blockId(to));
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("from", from.toString());
                json.addProperty("to", to.toString());
            }
        }
    }

    public static class Transforming extends TwilightForestDataProvider<Transforming.Builder> {
        public Transforming(String namespace, DataGenerator generator) {
            super("transformation_powder", namespace, generator);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The id of the base entity type to use.
         * @param to         The id of the result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, ResourceLocation from, ResourceLocation to, boolean reversible) {
            return new Builder(new ResourceLocation(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The id of the base entity type to use.
         * @param to         The result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, ResourceLocation from, EntityType<?> to, boolean reversible) {
            return new Builder(new ResourceLocation(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The base entity type to use.
         * @param to         The id of the result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, EntityType<?> from, ResourceLocation to, boolean reversible) {
            return new Builder(new ResourceLocation(namespace, id), from, to, reversible);
        }

        /**
         * @param id         The recipe id to use.
         * @param from       The base entity type to use.
         * @param to         The result entity type to use.
         * @param reversible Whether the transformation is reversible or not.
         */
        public Builder builder(String id, EntityType<?> from, EntityType<?> to, boolean reversible) {
            return new Builder(new ResourceLocation(namespace, id), from, to, reversible);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final ResourceLocation from;
            private final ResourceLocation to;
            private final boolean reversible;

            public Builder(ResourceLocation id, ResourceLocation from, ResourceLocation to, boolean reversible) {
                super(id);
                this.from = from;
                this.to = to;
                this.reversible = reversible;
            }

            public Builder(ResourceLocation id, ResourceLocation from, EntityType<?> to, boolean reversible) {
                this(id, from, Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(to)), reversible);
            }

            public Builder(ResourceLocation id, EntityType<?> from, ResourceLocation to, boolean reversible) {
                this(id, Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(from)), to, reversible);
            }

            public Builder(ResourceLocation id, EntityType<?> from, EntityType<?> to, boolean reversible) {
                this(id, Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(from)), Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(to)), reversible);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.addProperty("reversible", reversible);
                json.addProperty("from", from.toString());
                json.addProperty("to", to.toString());
            }
        }
    }
}
