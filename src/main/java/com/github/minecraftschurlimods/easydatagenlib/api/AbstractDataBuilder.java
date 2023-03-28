package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes">Custom Datagen Base Classes documentation</a>}
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
