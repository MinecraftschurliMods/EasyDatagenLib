package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * The abstract parent class of builder classes used by {@link AbstractDataProvider} for generating data contents.
 */
public abstract class AbstractDataBuilder<T extends AbstractDataBuilder<T>> {
    public final ResourceLocation id;

    /**
     * Creates a new builder with the given id.
     *
     * @param id The id to use. Should be unique within the same data provider and the same namespace.
     */
    public AbstractDataBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * Adds this builder's contents to the given {@link JsonObject}.
     */
    protected abstract void toJson(JsonObject json);
}
