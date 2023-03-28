package com.github.minecraftschurlimods.easydatagenlib.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

/**
 * {@see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes">Custom Datagen Base Classes documentation</a>}
 */
public abstract class AbstractRecipeBuilder<T extends AbstractRecipeBuilder<T>> extends AbstractDataBuilder<T> {
    public final List<ICondition> conditions = new ArrayList<>();

    /**
     * Creates a new builder with the given id.
     *
     * @param id The id to use. Should be unique within the same data provider and the same namespace.
     */
    public AbstractRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    /**
     * Adds a condition to this recipe.
     *
     * @param condition The condition to add.
     * @return This builder, for chaining.
     */
    @SuppressWarnings("unchecked")
    public T addCondition(ICondition condition) {
        conditions.add(condition);
        return (T) this;
    }
}
